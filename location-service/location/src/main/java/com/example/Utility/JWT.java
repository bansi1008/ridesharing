package com.example.Utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JWT {
    private static final String SECRET = "hellotheredkvdvdkvdfjvdfjvsdjcnsdjcnsdjvndfjvdfjiefjweiturigoasockasimd"; // 32+ chars
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());


  

    public boolean parseToken(String token) {
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
}
