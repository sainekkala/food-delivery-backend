package com.fooddelivery.auth_service.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	
	public JwtAuthFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String header = request.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer")) {
			String token = header.substring(7);
			try {
				Jws<Claims> parsed = jwtService.parseToken(token);
				Claims claims = parsed.getBody();
                String userId = claims.get("uid", String.class);
                var auth = new UsernamePasswordAuthenticationToken(userId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
			}catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or expired token");
                return;
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	

}
