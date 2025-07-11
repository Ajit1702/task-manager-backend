package com.springbootkafka.task_manager.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springbootkafka.task_manager.service.CustomUserDetailsService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.info("JWT Filter triggered");
		String header = request.getHeader("Authorization");
		log.debug("Authorization Header: {}", header);
		final String jwt;
		final String username;

		if (header == null || !header.startsWith("Bearer ")) {
			log.warn("Missing or invalid Authorization header");
			filterChain.doFilter(request, response);
			return;
		}

		jwt = header.substring(7);

		try {
			username = jwtUtil.extractUsername(jwt);
		} catch (JwtException e) {
			log.error("Failed to extract username from JWT: {}", e.getMessage());
			filterChain.doFilter(request, response);
			return;
		}
		log.info("Extracted username from token: {}", username);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			log.debug("Loaded user details for: {}", userDetails.getUsername());

			if (jwtUtil.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				log.info("Security context updated for user: {}", username);
			} else {
				log.warn("Invalid JWT token for user: {}", username);
			}

		}
		filterChain.doFilter(request, response);
	}

}
