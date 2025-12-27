package com.enigma.tp_api_rest.infrastructure.configurations;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfiguration {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Bean
    public SecretKey jwtSecretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Bean
    public Long jwtExpiration(){
        return expiration;
    }
}
