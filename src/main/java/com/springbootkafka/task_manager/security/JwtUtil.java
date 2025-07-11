package com.springbootkafka.task_manager.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "TWhFbnppVW9RM0dvaUFrdkFNVmtHRHk3d3lzSEl4bWk=";
	
	public String generateToken(UserDetails userDetails) {
		
		return  Jwts.builder()
				.setSubject(userDetails.getUsername() )
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60* 60))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.compact();
	}

	private static Key getSigningKey() {		 
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey()).build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
		.parseClaimsJws(token)
		.getBody()
		.getExpiration();
		return expiration.before(new Date());
	}
}
