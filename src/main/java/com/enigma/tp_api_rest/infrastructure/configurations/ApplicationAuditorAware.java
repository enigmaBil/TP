package com.enigma.tp_api_rest.infrastructure.configurations;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class ApplicationAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        //development purpose only
        return Optional.of("dev-user");

        //Production purpose
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.of("system");
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof UserDetails userDetails) {
//            return Optional.of(userDetails.getUsername());
//        } else if (principal instanceof String) {
//            return Optional.of((String) principal);
//        }
//
//        return Optional.of(principal.toString());
    }
}
