package com.ride.Utility;
import com.ride.Model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWT {
    private static final String SECRET = "hellotheredkvdvdkvdfjvdfjvsdjcnsdjcnsdjvndfjvdfjiefjweiturigoasockasimd"; // Use 32+ chars
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long expirationTime = 1000 * 60 * 60; 
        private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; 


    public String generateToken(String email, Role role, String name, Long userId) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }
    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }




    
}
