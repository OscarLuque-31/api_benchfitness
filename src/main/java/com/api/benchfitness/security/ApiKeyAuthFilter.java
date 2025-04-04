package com.api.benchfitness.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

	private static final String HEADER_NAME  = "x-api-key";
	private final String validApiKey;

	public ApiKeyAuthFilter(String validApiKey) {
		this.validApiKey = validApiKey;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String requestApiKey = request.getHeader(HEADER_NAME);
		
		// Valida si las apikey coinciden
		if (validApiKey.equals(requestApiKey)) {
			filterChain.doFilter(request,response);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("API Key inválida o faltante");
		}


	}

}
