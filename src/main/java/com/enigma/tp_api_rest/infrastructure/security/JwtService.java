package com.enigma.tp_api_rest.infrastructure.security;

import com.enigma.tp_api_rest.infrastructure.database.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private final Long jwtExpiration;

    @Value("${jwt.access-token-expiration}")
    private final Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private final Long refreshTokenExpiration;

    @Value("${jwt.email-verification-token-expiration}")
    private final Long emailVerificationTokenExpiration;

    @Value("${jwt.password-reset-token-expiration}")
    private final Long passwordResetTokenExpiration;

    /**
     * Génère un token JWT d'accès pour l'utilisateur donné.
     *
     * @param user L'utilisateur pour lequel générer le token.
     * @return Le token JWT d'accès sous forme de chaîne.
     * @throws IllegalArgumentException si l'utilisateur ou ses détails sont nuls.
     */
    public String generateAccessToken(User user){
        //validation des entrées
        if(user == null || user.getId() == null || user.getEmail() == null){
            log.error("Cannot generate access token: user or user details are null");
            throw new IllegalArgumentException("User and user details must not be null");
        }
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(accessTokenExpiration);
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userID", user.getId())
                .claim("tokenType", "ACCESS")
                .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Génère un token JWT de rafraîchissement pour l'utilisateur donné.
     *
     * @param user L'utilisateur pour lequel générer le token.
     * @return Le token JWT de rafraîchissement sous forme de chaîne.
     * @throws IllegalArgumentException si l'utilisateur ou ses détails sont nuls.
     */
    public String generateRefreshToken(User user){
        //validation des entrées
        if(user == null || user.getId() == null || user.getEmail() == null){
          log.error("Cannot generate refresh token: user or user details are null");
          throw new IllegalArgumentException("User and user details must not be null");
        }
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(refreshTokenExpiration);
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userID", user.getId())
                .claim("tokenType", "REFRESH")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Génère un token JWT de vérification d'email pour l'utilisateur donné.
     *
     * @param userId L'ID de l'utilisateur pour lequel générer le token.
     * @param email L'email de l'utilisateur pour lequel générer le token.
     * @return Le token JWT de vérification d'email sous forme de chaîne.
     * @throws IllegalArgumentException si l'ID utilisateur ou l'email sont nuls.
     */
    public String generateEmailVerificationToken(UUID userId, String email){
        //validation des entrées
        if(userId == null || email == null){
            log.error("Cannot generate email verification token: userId or email are null");
            throw new IllegalArgumentException("userId and email must not be null");
        }
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(emailVerificationTokenExpiration);
        return Jwts.builder()
                .subject(email)
                .claim("userID", userId)
                .claim("tokenType", "EMAIL_VERIFICATION")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Génère un token JWT de réinitialisation de mot de passe pour l'utilisateur donné.
     *
     * @param userId L'ID de l'utilisateur pour lequel générer le token.
     * @return Le token JWT de réinitialisation de mot de passe sous forme de chaîne.
     * @throws IllegalArgumentException si l'ID utilisateur est nul.
     */
    public String generatePasswordResetToken(UUID userId){
        //validation des entrées
        if(userId == null){
            log.error("Cannot generate password reset token: userId is null");
            throw new IllegalArgumentException("userId must not be null");
        }
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(passwordResetTokenExpiration);
        return Jwts.builder()
                .claim("userID", userId)
                .claim("tokenType", "PASSWORD_RESET")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }

    /* Methodes utilitaire pour extraction des claims des tokens et de validation de tokens */

    /**
     * Valide le token JWT donné.
     *
     * @param token Le token JWT à valider.
     * @return TRUE si le token est valide, FALSE sinon.
     */
    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        }
        catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        }
        catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }
        catch (ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        }
        catch (UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}", e.getMessage());
        }
        catch (IllegalArgumentException e){
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }



    public String getEmailFormAccessToken(String token) {
        return getCaimFromToken(token, Claims::getSubject);
    }

    public UUID getUserIdFromAccessToken(String token) {
        return getCaimFromToken(token, claims -> claims.get("userId", UUID.class));
    }

    public <T> T getCaimFromToken(String token, Function<Claims, T> claimsResolver){
        if(token == null || token.isEmpty()){
            log.warn("Attempted to extract claim from null or empty token");
            throw  new IllegalArgumentException("Token must not be null or empty");
        }
        try{
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        }catch (JwtException e) {
            log.error("Failed to get claim from token due to JWT parsing error", e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while getting claim from token", e);
            return null;
        }
    }

    public List<String> getRolesFromAccessToken(String token) {
        List<?> rolesList = getCaimFromToken(token, claims -> claims.get("roles", List.class));

        if (rolesList == null || rolesList.isEmpty()) {
            log.debug("No roles found in access token or roles claim is null/empty.");
            return Collections.emptyList();
        }

        try {
            return rolesList.stream()
                    .filter(Objects::nonNull) // Filtre les éléments null
                    .map(Object::toString)   // Convertit en String
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error processing roles list from access token", e);
            return Collections.emptyList();
        }
    }

    public String getUserEmailFromToken(String token) {
        return getCaimFromToken(token, Claims::getSubject);
    }

    public Long getUserIdFromVerificationToken(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Attempted to get user ID from null or empty verification token.");
            return null;
        }

        try {
            Claims claims = getAllClaimsFromToken(token);

            // Vérifier que c'est bien un token de vérification
            if (!"email-verification".equals(claims.getSubject())) {
                log.warn("Token subject '{}' is not 'email-verification'", claims.getSubject());
                return null;
            }

            Number userIdObj = claims.get("userId", Number.class);
            return userIdObj != null ? userIdObj.longValue() : null;

        } catch (JwtException e) {
            log.error("Failed to get user ID from verification token due to JWT error", e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while getting user ID from verification token", e);
            return null;
        }
    }

    public Long getUserIdFromPasswordResetToken(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Attempted to get user ID from null or empty password reset token.");
            return null;
        }
        try {
            Claims claims = getAllClaimsFromToken(token);

            // Vérifier que c'est bien un token de reinitialisation
            if (!"password-reset".equals(claims.getSubject())) {
                log.warn("Token subject '{}' is not 'password-reset'", claims.getSubject());
                return null;
            }

            Number userIdObj = claims.get("userId", Number.class);
            return userIdObj != null ? userIdObj.longValue() : null;
        } catch (Exception e) { // Attrape JwtException, IllegalArgumentException, etc.
            log.error("Failed to get user ID from password reset token", e);
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getCaimFromToken(token, Claims::getExpiration);
        // Si expiration est null (token invalide, claim absent), on considère qu'il est expiré.
        // Sinon, on compare avec la date actuelle.
        return expiration == null || expiration.before(new Date());
    }

    private Claims getAllClaimsFromToken(String token) {
        if(token == null || token.isEmpty()){
            log.warn("Attempted to extract claims from null or empty token");
            throw  new IllegalArgumentException("Token must not be null or empty");
        }

        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (JwtException e) {
            log.error("Failed to parse JWT token", e);
            throw e;
        }
    }

    public String generateCredentialVerificationToken(UUID id, String email, String tempPassword) {
        return null;
    }
}
