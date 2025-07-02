package com.api.benchfitness.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.benchfitness.services.UserService;

/**
 * Configuración principal de seguridad para la aplicación.
 * Define la cadena de filtros de seguridad y la política de acceso.
 */
@Configuration // Indica que esta clase contiene configuraciones de Spring
public class SecurityConfig {

   
  
    @Autowired
    private final UserService userService; // Inyecta el UserService


    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Configura la cadena principal de filtros de seguridad.
     * @param http Objeto de configuración de seguridad HTTP
     * @return SecurityFilterChain configurado
     * @throws Exception Si hay errores en la configuración
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. Creamos el filtro personalizado de API Key
        ApiKeyAuthFilter apiKeyFilter = new ApiKeyAuthFilter(userService);

        // 2. Configuramos las reglas de seguridad
        http
            // 2.1. Desactivamos CSRF (Cross-Site Request Forgery)
            .csrf(csrf -> csrf.disable())
            
            // 2.2. Configuramos las autorizaciones de peticiones HTTP
            .authorizeHttpRequests(auth -> auth
                 // Permitimos que la ruta de registro sea accesible sin autenticación
            	.requestMatchers("/public/register").permitAll()
                // Todas las peticiones requieren autenticación
                .anyRequest().authenticated()
            )
            
            // 2.3. Añadimos nuestro filtro personalizado ANTES del filtro de autenticación básica
            .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);

        // 3. Construimos y devolvemos la configuración
        return http.build();
    }
}