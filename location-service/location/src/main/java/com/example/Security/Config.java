package com.example.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Auth.JwtAuth;

@Configuration
public class Config {

    private final JwtAuth auth;
    public Config(JwtAuth auth){
        this.auth=auth;
    }
 
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/api/locationrequest").authenticated() // JWT required
            .anyRequest().authenticated()
        )
        .addFilterBefore(auth, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}




    // @Bean()
    // @Order(1)
    // public SecurityFilterChain publicApiSecurityFilterChain(HttpSecurity http) throws Exception{
    //     http
    //     .securityMatcher("/api/location") 
    //     .csrf(csrf -> csrf.disable())
    //     .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());  
        

    // return http.build();
    // }



    


    
}
