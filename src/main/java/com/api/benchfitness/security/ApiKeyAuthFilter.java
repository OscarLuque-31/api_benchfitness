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

        // Imprime la clave de la solicitud y la clave esperada para depurar
        System.out.println("API Key proporcionada en la solicitud: " + requestApiKey);
        System.out.println("API Key configurada en el servidor: " + validApiKey);

        // Validación de la API Key
        if (requestApiKey != null && validApiKey.equals(requestApiKey)) {
            System.out.println("La clave es valida. Continuando con la solicitud...");
            filterChain.doFilter(request, response);  // Continuamos con la cadena de filtros si la API Key es válida
        } else {
            // Si la clave no es válida o falta, respondemos con un 403 Forbidden
            System.out.println("API Key invalida o faltante. Respondiendo con 403 Forbidden.");
            response.setStatus(HttpStatus.FORBIDDEN.value()); // Cambié esto de UNAUTHORIZED a FORBIDDEN para un mejor manejo de claves incorrectas
            response.getWriter().write("API Key invalida o faltante");
        }
    }
}
