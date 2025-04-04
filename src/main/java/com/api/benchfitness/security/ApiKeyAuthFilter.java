package com.api.benchfitness.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticación por API Key para Spring Security.
 * Valida las peticiones comparando la API Key recibida en el header 'x-api-key'
 * con la clave configurada en el servidor.
 */
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    // Logger para seguimiento de eventos
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyAuthFilter.class);
    
    // Nombre del header donde se espera la API Key
    private static final String HEADER_NAME = "x-api-key";
    
    // API Key válida (inyectada desde la configuración)
    private final String validApiKey;

    /**
     * Constructor del filtro.
     * @param validApiKey La API Key válida para autenticación
     * @throws IllegalArgumentException si la API Key es nula o vacía
     */
    public ApiKeyAuthFilter(String validApiKey) {
        if (validApiKey == null || validApiKey.isBlank()) {
            throw new IllegalArgumentException("La API Key no puede ser nula o vacía");
        }
        this.validApiKey = validApiKey;
        // Log seguro: solo muestra los últimos 2 caracteres de la clave real
        LOGGER.info("Filtro de API Key inicializado. Clave esperada: ****{}", 
                   validApiKey.substring(validApiKey.length() - 2));
    }

    /**
     * Método principal del filtro que procesa cada petición.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Obtener información de la petición para logs
        String requestApiKey = request.getHeader(HEADER_NAME);
        String requestUri = request.getRequestURI();
        String clientIp = request.getRemoteAddr();
        
        // 2. Log de depuración (nivel DEBUG)
        LOGGER.debug("Validando petición: {} {} desde IP: {}", 
                   request.getMethod(), requestUri, clientIp);
        
        // 3. Validar presencia de API Key
        if (requestApiKey == null) {
            LOGGER.warn("Acceso no autorizado: falta API Key - Ruta: {} - IP: {}", requestUri, clientIp);
            sendErrorResponse(response, "API Key requerida");
            return;
        }
        
        // 4. Validar coincidencia de API Key
        if (!validApiKey.equals(requestApiKey)) {
            LOGGER.warn("Acceso no autorizado: API Key inválida - Ruta: {} - IP: {}", 
                       requestUri, clientIp);
            sendErrorResponse(response, "API Key inválida");
            return;
        }
        
        // 5. Autenticación exitosa
        LOGGER.debug("Autenticación exitosa para {}", requestUri);
        Authentication auth = new ApiKeyAuthentication(requestApiKey);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        // 6. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
    
    /**
     * Envía una respuesta de error HTTP 403 (Forbidden).
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
        response.getWriter().flush();
    }

    /**
     * Implementación de Authentication para API Keys.
     */
    private static class ApiKeyAuthentication implements Authentication {
        private static final long serialVersionUID = 1L;
        private final String apiKey;
        private boolean authenticated = true;

        public ApiKeyAuthentication(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public String getName() {
            return apiKey;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList(); // Sin roles/permissions
        }

        @Override
        public Object getCredentials() {
            return null; // No se usan credenciales adicionales
        }

        @Override
        public Object getDetails() {
            return null; // No hay detalles adicionales
        }

        @Override
        public Object getPrincipal() {
            return apiKey; // La API Key es el principal
        }

        @Override
        public boolean isAuthenticated() {
            return authenticated;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            this.authenticated = isAuthenticated;
        }
    }
}