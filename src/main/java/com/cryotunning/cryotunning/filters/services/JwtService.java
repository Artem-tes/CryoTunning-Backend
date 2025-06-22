package com.cryotunning.cryotunning.filters.services;

import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Base64;
import java.util.Date;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final long TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 100; // 100 дней в миллисекундах
    private static final String SECRET_KEY = "bQvYcVQ6GrMGsvMeLZ1Fas5HO+9Vq8coJx+0P4AiyjY=";

    private final Key signingKey;

    public JwtService() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Извлечение имени пользователя из токена
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Проверка валидности токена
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
