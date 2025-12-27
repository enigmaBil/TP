package com.enigma.tp_api_rest.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.debug("Processing JWT authentication filter: {} {}", request.getMethod() ,request.getRequestURI());
        // Extract JWT from Authorization header
        final String jwtToken;
        final String userEmail;

        // Bypass pour les requêtes CORS preflight
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            return;
        }

        //extraire le token
        jwtToken = extractJwtFromCookie(request);
        if (jwtToken == null || jwtToken.isBlank()) {
            log.debug("No valid JWT token found in cookies. Proceeding without authentication.");
            filterChain.doFilter(request, response);
            return;
        }
        log.debug("Extracted JWT token from cookie");

        // Extract user email from JWT
        userEmail = jwtService.getEmailFormAccessToken(jwtToken);
        if (userEmail == null) {
            log.warn("Invalid or expired JWT token. Cannot extract user email.");
            filterChain.doFilter(request, response);
            return;
        }
        log.debug("Extracted user email from JWT token: {}", userEmail);

        //verifier l'etat de l'utilisateur dans le contexte de securité
        //et verifier que le token est valide
        if (SecurityContextHolder.getContext().getAuthentication() == null && jwtService.validateToken(jwtToken)) {
            //charger les détails de l'utilisateur depuis la base de données.
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            //vérifier que l'email du token correspond à l'email de l'utilisateur chargé
            if(userDetails.getUsername().equals(userEmail)) {
                //créer un objet d'authentification spring security
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // ajouter les détails de la requête web (IP, session ID, etc.)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //définir l'authentification dans le SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("User {} successfully authenticated via JWT.", userEmail);
            }else {
                log.warn("Token email {} does not match loaded user email {}.", userEmail, userDetails.getUsername());
            }
        } else {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                log.debug("User already authenticated. Skipping JWT filter.");
            } else {
                log.warn("JWT token validation failed for user {}.", userEmail);
            }
        }

        //passer la requete au filtre suivant dans la chaine.
        filterChain.doFilter(request, response);
    }
    private String extractJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (token != null && !token.isBlank()) {
                        log.trace("JWT token extracted from cookie: {}", cookie.getName());
                        return token;
                    } else {
                        log.debug("JWT token cookie '{}' found but value is null or blank.", cookie.getName());
                    }
                }
            }
        }
        log.debug("No JWT token found in cookies.");
        return null;
    }
}
