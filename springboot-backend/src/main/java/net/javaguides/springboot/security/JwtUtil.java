package net.javaguides.springboot.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String jwtSecret = "jxsDRmOvlmlZklsSO2kuE6Et6C2RYfkMbCpsszGBplkXMPbDBw68HoqldrMl8QK3N0VE48sERpSV4liDX4qFUg==";
    private final String jwtIssuer = "issuer";

    // Method to generate token
    public String generateToken(String empId) {
        return Jwts.builder()
                .setSubject(empId)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Signing the JWT with HS512
                .compact();
    }

    // Method to extract empId from token
    public String extractEmpId(String token) {
        return extractClaim(token, Claims::getSubject); // Directly use the Claims' getSubject method
    }

    // Method to extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Use Claims' getExpiration method
    }

    // Method to extract any claim using a claimsResolver function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder() // Using parserBuilder (newer method)
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    // Method to validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder() // Using parserBuilder (newer method)
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token); // Will throw an exception if the token is invalid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Handle invalid token, log the exception, etc.
        }
        return false;
    }
}
