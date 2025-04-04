package com.api.benchfitness.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Value("${security.api.key}")
	private String apiKey;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		ApiKeyAuthFilter apiKeyFilter = new ApiKeyAuthFilter(apiKey);

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/public/**").permitAll().anyRequest().authenticated()).addFilterBefore(apiKeyFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
	}
}
