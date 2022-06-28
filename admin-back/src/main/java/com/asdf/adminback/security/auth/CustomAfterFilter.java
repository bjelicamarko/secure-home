package com.asdf.adminback.security.auth;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAfterFilter extends OncePerRequestFilter {

    public static final int MAX_AGE = 31536000; // one year

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Strict-Transport-Security: max-age=<expire-time>; includeSubDomains
        response.addHeader("Strict-Transport-Security", String.format("max-age=%d; includeSubDomains", MAX_AGE));
        response.addHeader("Cache-control", "no-store");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("X-Frame-Options", "DENY");
        filterChain.doFilter(request, response);
    }
}
