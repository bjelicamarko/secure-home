package com.asdf.myhomeback.security.auth;

import com.asdf.myhomeback.exceptions.TokenBlacklistedException;
import com.asdf.myhomeback.models.BlacklistedToken;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.services.AppUserService;
import com.asdf.myhomeback.services.BlacklistedTokenService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.utils.LogMessGen;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;

	private AppUserService appUserService;

	private BlacklistedTokenService blackListedTokenService;

	private LogService logService;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService, BlacklistedTokenService blackListedTokenService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
		this.blackListedTokenService = blackListedTokenService;
	}

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService, AppUserService appUserService, BlacklistedTokenService blackListedTokenService, LogService logService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
		this.blackListedTokenService = blackListedTokenService;
		this.appUserService = appUserService;
		this.logService = logService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenUtils.getToken(request);
		String urlPath = request.getRequestURI();

		String fingerprint = tokenUtils.getFingerprintFromCookie(request);

		try {
			if (authToken != null) {
				username = tokenUtils.getUsernameFromToken(authToken);
				if (username != null) {
					if(!appUserService.isUserLocked(username)) {
						throw new LockedException("User '" + username + "' is locked.");
					}
					// check if token on blacklist
					BlacklistedToken blacklistedToken = blackListedTokenService.getBlackListedToken(authToken);
					if (blacklistedToken != null)
						throw new TokenBlacklistedException();
					// get user with username
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					if (tokenUtils.validateToken(authToken, userDetails, fingerprint)) {
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		} catch (ExpiredJwtException ex) {
			username = tokenUtils.getUsernameFromToken(authToken);
			logService.generateWarnLog(LogMessGen.expiredJWT(username, urlPath));
			LOGGER.debug("Token expired!");
		} catch (LockedException e) {
			username = tokenUtils.getUsernameFromToken(authToken);
			logService.generateWarnLog(LogMessGen.accountLocked(request.getRequestURL().toString(), username));
		}
		catch (TokenBlacklistedException e){
			username = tokenUtils.getUsernameFromToken(authToken);
			logService.generateWarnLog(LogMessGen.blacklistedToken(username, urlPath));
			System.err.println(e.getMessage());
			LOGGER.debug(e.getMessage());
		}

		chain.doFilter(request, response);
	}

}