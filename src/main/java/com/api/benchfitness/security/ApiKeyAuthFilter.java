package com.api.benchfitness.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.benchfitness.models.User;
import com.api.benchfitness.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticación por API Key para Spring Security. Valida las
 * peticiones comparando la API Key recibida en el header 'x-api-key' con la
 * clave configurada en el servidor.
 */
public class ApiKeyAuthFilter extends OncePerRequestFilter {

	// Logger para seguimiento de eventos
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiKeyAuthFilter.class);

	// Nombre del header donde se espera la API Key
	private static final String HEADER_NAME = "x-api-key";

	@Autowired
	private final UserService userService;

	/**
	 * Constructor del filtro.
	 * 
	 * @param validApiKey La API Key válida para autenticación
	 * @throws IllegalArgumentException si la API Key es nula o vacía
	 */
	public ApiKeyAuthFilter(UserService userService) {
		this.userService = userService;
		// Log seguro: solo muestra los últimos 2 caracteres de la clave real
		LOGGER.info("Filtro de API Key inicializado con UserService.");
	}

	/**
	 * Método principal del filtro que procesa cada petición.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestUri = request.getRequestURI();

		// **Paso 1: Permitir acceso a la ruta de registro sin API Key**
		// Es crucial que esta ruta sea pública antes de cualquier validación de clave.
		if (requestUri.equals("/public/register")) {
			LOGGER.debug("Ruta de registro: {} - Saltando validación de API Key.", requestUri);
			filterChain.doFilter(request, response);
			return;
		}

		// --- A partir de aquí, todas las demás rutas requieren API Key ---

		String requestApiKey = request.getHeader(HEADER_NAME);
		String clientIp = request.getRemoteAddr();

		LOGGER.debug("Validando petición: {} {} desde IP: {}", request.getMethod(), requestUri, clientIp);

		// **Paso 2: Validar presencia de API Key en el encabezado**
		if (requestApiKey == null || requestApiKey.isBlank()) {
			LOGGER.warn("Acceso no autorizado: falta API Key - Ruta: {} - IP: {}", requestUri, clientIp);
			sendErrorResponse(response, "API Key requerida");
			return;
		}

		// **Paso 3: Validar la API Key contra la base de datos usando UserService**
        Optional<User> userOptional = userService.validateApiKey(requestApiKey);

        if (userOptional.isEmpty()) {
            LOGGER.warn("Acceso no autorizado: API Key inválida o inactiva - Ruta: {} - IP: {}",
                        requestUri, clientIp);
            sendErrorResponse(response, "API Key inválida o inactiva");
            return;
        }

     // **Paso 4: Autenticación exitosa**
        User user = userOptional.get();
        LOGGER.debug("Autenticación exitosa para owner: {} en ruta: {}", user.getOwner(), requestUri);

        // Creamos y establecemos el objeto de autenticación en el SecurityContext
        // Ahora pasamos el objeto User a nuestra clase de autenticación.
        Authentication auth = new ApiKeyAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // **Paso 5: Continuar con la cadena de filtros**
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
     * Ahora encapsula el objeto User autenticado.
     */
	private static class ApiKeyAuthentication implements Authentication {
        private static final long serialVersionUID = 1L;
        private final User user; // Ahora guarda el objeto User en lugar de solo la clave
        private boolean authenticated = true;

        public ApiKeyAuthentication(User user) {
            this.user = user;
        }

        @Override
        public String getName() {
            return user.getOwner(); // Usa el 'owner' del usuario como nombre principal
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            // Aquí podrías añadir roles si tu entidad User tuviera un campo de rol.
            // Por ahora, devolvemos una colección vacía.
            return Collections.emptyList();
        }

        @Override
        public Object getCredentials() {
            return null; // La API Key no se expone aquí como credencial
        }

        @Override
        public Object getDetails() {
            return null; // Detalles adicionales, si los hubiera
        }

        @Override
        public Object getPrincipal() {
            return user; // El objeto User completo es el principal
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