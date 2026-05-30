package com.fooddelivery.auth_service.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	 private final Key key;
	    private final long accessTokenMs;

	    public JwtService(@Value("${jwt.secret}") String secret,
	                      @Value("${jwt.access-token-ms}") long accessTokenMs) {
	        this.key = Keys.hmacShaKeyFor(secret.getBytes());
	        this.accessTokenMs = accessTokenMs;
	    }

	    public String generateToken(String subject, Map<String, Object> claims) {
	        long now = System.currentTimeMillis();
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(now))
	                .setExpiration(new Date(now + accessTokenMs))
	                .signWith(key, SignatureAlgorithm.HS256)
	                .compact();
	    }

	    public Jws<Claims> parseToken(String token) throws JwtException {
	        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
	    }

}
