package org.africa.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration CORS centralisée.
 * Autorise les appels depuis le front React (Vite dev server).
 *
 * NOTE : En production, remplacer "http://localhost:5173"
 * par l'URL réelle du front déployé.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Origines autorisées
        config.addAllowedOrigin("http://localhost:5173");  // Vite dev
        config.addAllowedOrigin("http://localhost:3000");  // Create React App (si besoin)

        // Méthodes HTTP autorisées
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");  // Obligatoire pour le preflight

        // Headers autorisés
        config.addAllowedHeader("*");

        // Autoriser les cookies et credentials si nécessaire (JWT plus tard)
        config.setAllowCredentials(true);

        // Durée de cache du preflight (en secondes) — évite les requêtes OPTIONS répétées
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}