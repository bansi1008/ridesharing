package com.example.Auth;
import com.example.Utility.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.validation.Valid;

import com.example.Utility.JWT;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuth extends OncePerRequestFilter {
    private final JWT jwt;
    public JwtAuth(JWT jwt) {
        this.jwt = jwt;
    }

  @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
                                throws ServletException, IOException {

    try {
        String token = extractToken(request);

        if (token != null && jwt.parseToken(token)) {
            Long userId = jwt.getUserId(token);
            

            if (userId != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));
                authentication.setDetails(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
    } catch (Exception e) {
       
    }

    filterChain.doFilter(request, response);
}


    private String extractToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("ridesite_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }


    
}
