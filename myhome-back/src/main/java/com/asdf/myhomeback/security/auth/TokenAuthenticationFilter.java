package com.asdf.myhomeback.security.auth;

import com.asdf.myhomeback.Exception.TokenBlacklistedException;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.services.BlacklistedTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private BlacklistedTokenService blackListedTokenService;

	protected final Log LOGGER = LogFactory.getLog(getClass());

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService, BlacklistedTokenService blackListedTokenService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
		this.blackListedTokenService = blackListedTokenService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenUtils.getToken(request);

		String fingerprint = tokenUtils.getFingerprintFromCookie(request);

		try {
			if (authToken != null) {
				username = tokenUtils.getUsernameFromToken(authToken);
				if (username != null) {
					// check if token on blacklist
					String uuid = tokenUtils.getUUIDFromToken(authToken);
					if (blackListedTokenService.isTokenBlackListed(uuid))
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
			LOGGER.debug("Token expired!");
		}
		catch (TokenBlacklistedException e){
			System.err.println(e.getMessage());
			LOGGER.debug(e.getMessage());
		}

		chain.doFilter(request, response);
	}

}