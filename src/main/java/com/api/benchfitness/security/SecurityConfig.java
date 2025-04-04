package com.api.benchfitness.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Value("${security.api.key}")
	private String apiKey;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		ApiKeyAuthFilter apiKeyFilter = new ApiKeyAuthFilter(apiKey);

		 http.csrf(csrf -> csrf.disable()) // Desactiva CSRF si no es necesario
	        .authorizeHttpRequests(auth -> auth
	            .anyRequest().authenticated()) // Permite todas las solicitudes para depurar
	        .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class); // Añadimos el filtro de API Key

		return http.build();
	}
}
