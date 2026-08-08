package com.ramesh.ecommerce.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String email)
    {
        SecretKey key = getSigningKey();

        return Jwts.builder().
                subject(email).
                issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+expiration)).
                signWith(key).
                compact();
    }

    public SecretKey getSigningKey()
    {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractAllClaims(String token)
    {
        return Jwts.parser().
                verifyWith(getSigningKey()).
                build().
                parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }

    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );

    }

    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );

    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());

    }

    public boolean isTokenValid(
            String token,
            String email) {

        String username =
                extractUsername(token);

        return username.equals(email)
                &&
                !isTokenExpired(token);

    }
}
