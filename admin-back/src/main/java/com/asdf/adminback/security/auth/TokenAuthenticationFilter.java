package com.asdf.adminback.security.auth;

import com.asdf.adminback.security.TokenUtils;
import com.asdf.adminback.services.AppUserService;
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

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService, AppUserService appUserService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
		this.appUserService = appUserService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenUtils.getToken(request);

		String fingerprint = tokenUtils.getFingerprintFromCookie(request);

		if (authToken != null) {
			username = tokenUtils.getUsernameFromToken(authToken);
			if (username != null) {
				if(appUserService.isUserLocked(username)) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					if (tokenUtils.validateToken(authToken, userDetails, fingerprint)) {
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

}