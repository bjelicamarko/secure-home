package com.asdf.myhomeback.security;

import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.models.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenUtils {

	@Value("myhome-app")
	private String APP_NAME;

	@Value("somesecret")
	public String SECRET;

	@Value("900000")
	private int EXPIRES_IN;

	@Value("Authorization")
	private String AUTH_HEADER;


	private static final String AUDIENCE_WEB = "web";
	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	private final SecureRandom secureRandom = new SecureRandom();

	public String generateToken(String username, List<UserRole> roles, String fingerprint) {
		String fingerprintHash = generateFingerprintHash(fingerprint);
		String[] roleNames = roles.stream().map(UserRole::getName).collect(Collectors.toList()).toArray(String[]::new);
		return Jwts.builder()
				.setIssuer(APP_NAME)
				.setSubject(username)
				.setAudience(AUDIENCE_WEB)
				.setIssuedAt(new Date())
				.setExpiration(generateExpirationDate())
				.claim("roles", roleNames)
				.claim("userFingerprint", fingerprintHash)
				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();

	}

	public String generateFingerprint() {
		byte[] randomFgp = new byte[50];
		this.secureRandom.nextBytes(randomFgp);
		return DatatypeConverter.printHexBinary(randomFgp);
	}

	private String generateFingerprintHash(String userFingerprint) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes(StandardCharsets.UTF_8));
			return DatatypeConverter.printHexBinary(userFingerprintDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	private Date generateExpirationDate() {
		return new Date(new Date().getTime() + EXPIRES_IN);
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public int getExpiredIn() {
		return EXPIRES_IN;
	}

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	public String getFingerprintFromCookie(HttpServletRequest request) {
		String userFingerprint = null;
		if (request.getCookies() != null && request.getCookies().length > 0) {
			List<Cookie> cookies = Arrays.stream(request.getCookies()).collect(Collectors.toList());
			Optional<Cookie> cookie = cookies.stream().filter(c -> "__Secure-Fgp".equals(c.getName())).findFirst();

			if (cookie.isPresent()) {
				userFingerprint = cookie.get().getValue();
			}
		}
		return userFingerprint;
	}

	private String getFingerprintFromToken(String token) {
		String fingerprint;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			fingerprint = claims.get("userFingerprint", String.class);
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (Exception e) {
			fingerprint = null;
		}
		return fingerprint;
	}

	private String getAlgorithmFromToken(String token) {
		String algorithm;
		try {
			algorithm = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getHeader()
					.getAlgorithm();
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (Exception e) {
			algorithm = null;
		}
		return algorithm;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public Boolean validateToken(String token, UserDetails userDetails, String fingerprint) {
		AppUser user = (AppUser) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);

		// Token je validan kada:
		boolean isUsernameValid = username != null // korisnicko ime nije null
				&& username.equals(userDetails.getUsername()); // korisnicko ime iz tokena se podudara sa korisnickom imenom koje pise u bazi
				// && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()); // nakon kreiranja tokena korisnik nije menjao svoju lozinku

		// Validiranje fingerprint-a
		System.out.println("FGP ===> " + fingerprint);
		boolean isFingerprintValid = false;
		boolean isAlgorithmValid = false;
		if (fingerprint != null) {
			isFingerprintValid = validateTokenFingerprint(fingerprint, token);
			isAlgorithmValid = SIGNATURE_ALGORITHM.getValue().equals(getAlgorithmFromToken(token));
		}
		return isUsernameValid && isFingerprintValid && isAlgorithmValid;
	}

	private boolean validateTokenFingerprint(String fingerprint, String token) {
		// Hesiranje fingerprint-a radi poređenja sa hesiranim fingerprint-om u tokenu
		String fingerprintHash = generateFingerprintHash(fingerprint);
		String fingerprintFromToken = getFingerprintFromToken(token);
		return fingerprintFromToken.equals(fingerprintHash);
	}

	public String getUsernameFromRequest(HttpServletRequest request) {
		String token = this.getToken(request);
		return this.getUsernameFromToken(token);
	}
}