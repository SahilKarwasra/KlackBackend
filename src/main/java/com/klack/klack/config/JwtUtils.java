package com.klack.klack.config;

import com.klack.klack.entity.Users;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // Use a proper base64-encoded secret that's long enough for HS512
    private final String jwtSecret = "sGjQvatMsL4CIqsHKpohz2C80VnFxolmJOqWYy6P0I28oubCwf/JBt5btEwgZyfTYFgQFDMw1Dc+sMF3MhvmOA=="; // Base64 encoded
    private final long jwtExpirationMs = 86400000; // 1 day

    private Key getSigningKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate token
    public String generateToken(Users user) {
        System.out.println("JwtUtils: Generating token for user: " + user.getEmail());
        try {
            String token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .claim("role", user.getRole().name())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
            System.out.println("JwtUtils: Token generated successfully");
            return token;
        } catch (Exception e) {
            System.out.println("JwtUtils: Error generating token: " + e.getMessage());
            throw e;
        }
    }

    // Extract email
    public String getEmailFromToken(String token) {
        System.out.println("JwtUtils: Extracting email from token");
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            System.out.println("JwtUtils: Validating token");
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            System.out.println("JwtUtils: Token is valid");
            return true;
        } catch (JwtException e) {
            System.out.println("JwtUtils: Token validation failed: " + e.getMessage());
            return false;
        }
    }
}
















