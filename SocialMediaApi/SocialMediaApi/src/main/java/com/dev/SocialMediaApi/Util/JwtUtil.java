package com.dev.SocialMediaApi.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT Utility class for token generation and validation
 *
 * Handles JWT token creation, parsing, and validation
 * Uses HMAC-SHA256 for token signing
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // Secret key for JWT signing (In production, this should be externalized)
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token expiration time (24 hours)
    private final long JWT_EXPIRATION = 86400000;

    /**
     * Generates JWT token with username as subject.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Validates the token and returns the username if valid.
     */

    public String validateToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
