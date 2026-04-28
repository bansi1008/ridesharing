package com.example.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import org.springframework.stereotype.Component;

@Component
public class JWT {
    private static final String SECRET = "hellotheredkvdvdkvdfjvdfjvsdjcnsdjcnsdjvndfjvdfjiefjweiturigoasockasimd"; // Use
                                                                                                                    // 32+
                                                                                                                    // chars
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public boolean parseToken(String token) {
        System.out.println("Parsing token: " + token);
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }

    }

    public Long getUserId(String token) {
        Number userIdNumber = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Number.class);
        return userIdNumber != null ? userIdNumber.longValue() : null;
    }

    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public String getName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("name", String.class);
    }

}