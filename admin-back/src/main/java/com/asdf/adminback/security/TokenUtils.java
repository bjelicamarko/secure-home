package com.asdf.adminback.security;

import org.bouncycastle.crypto.tls.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

	@Value("restaurant-app")
	private String APP_NAME;

	@Value("somesecret")
	public String SECRET;

	@Value("3600000")
	private int EXPIRES_IN;

	@Value("Authorization")
	private String AUTH_HEADER;

	private static final String AUDIENCE_WEB = "web";
	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";

//	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
//
//	public String generateToken(String email, String userType, Long id) {
//		return Jwts.builder()
//				.setIssuer(APP_NAME)
//				.setSubject(email)
//				.setAudience(AUDIENCE_WEB)
//				.setIssuedAt(new Date())
//				.setExpiration(generateExpirationDate())
//				// .claim("key", value) //moguce je postavljanje proizvoljnih podataka u telo JWT tokena
//				.claim("role", userType) //moguce je i staviti string koji ce predstavljati listu rola korisnika kao
//											//ali svakako kod nas moze imati samo jednu rolu tj usertype
//				.claim("userId", id)
//				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
//	}
//
//	private Date generateExpirationDate() {
//		return new Date(new Date().getTime() + EXPIRES_IN);
//	}
//
//	public String refreshToken(String token) {
//		String refreshedToken;
//		try {
//			final Claims claims = this.getAllClaimsFromToken(token);
//			claims.setIssuedAt(new Date());
//			refreshedToken = Jwts.builder()
//					.setClaims(claims)
//					.setExpiration(generateExpirationDate())
//					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
//		} catch (Exception e) {
//			refreshedToken = null;
//		}
//		return refreshedToken;
//	}
//
//	public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//		final Date created = this.getIssuedAtDateFromToken(token);
//		return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
//				&& (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
//	}
//
//	public Boolean validateToken(String token, UserDetails userDetails) {
//		AppUser user = (AppUser) userDetails;
//		final String username = getUsernameFromToken(token);
//		final Date created = getIssuedAtDateFromToken(token);
//		return username != null && username.equals(userDetails.getUsername());
//	}
//
//	public String getUsernameFromToken(String token) {
//		String username;
//		try {
//			final Claims claims = this.getAllClaimsFromToken(token);
//			username = claims.getSubject();
//		} catch (Exception e) {
//			username = null;
//		}
//		return username;
//	}
//
//	public Date getIssuedAtDateFromToken(String token) {
//		Date issueAt;
//		try {
//			final Claims claims = this.getAllClaimsFromToken(token);
//			issueAt = claims.getIssuedAt();
//		} catch (Exception e) {
//			issueAt = null;
//		}
//		return issueAt;
//	}
//
//	public String getAudienceFromToken(String token) {
//		String audience;
//		try {
//			final Claims claims = this.getAllClaimsFromToken(token);
//			audience = claims.getAudience();
//		} catch (Exception e) {
//			audience = null;
//		}
//		return audience;
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		Date expiration;
//		try {
//			final Claims claims = this.getAllClaimsFromToken(token);
//			expiration = claims.getExpiration();
//		} catch (Exception e) {
//			expiration = null;
//		}
//		return expiration;
//	}
//
//	public int getExpiredIn() {
//		return EXPIRES_IN;
//	}
//
//	public String getToken(HttpServletRequest request) {
//		String authHeader = getAuthHeaderFromHeader(request);
//		if (authHeader != null && authHeader.startsWith("Bearer ")) {
//			return authHeader.substring(7);
//		}
//		return null;
//	}
//
//	public String getAuthHeaderFromHeader(HttpServletRequest request) {
//		return request.getHeader(AUTH_HEADER);
//	}
//
//	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//		return (lastPasswordReset != null && created.before(lastPasswordReset));
//	}
//
//	private Boolean isTokenExpired(String token) {
//		final Date expiration = this.getExpirationDateFromToken(token);
//		return expiration.before(new Date());
//	}
//
//	private Boolean ignoreTokenExpiration(String token) {
//		String audience = this.getAudienceFromToken(token);
//		return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
//	}
//
//	private Claims getAllClaimsFromToken(String token) {
//		Claims claims;
//		try {
//			claims = Jwts.parser()
//					.setSigningKey(SECRET)
//					.parseClaimsJws(token)
//					.getBody();
//		} catch (Exception e) {
//			claims = null;
//		}
//		return claims;
//	}

}