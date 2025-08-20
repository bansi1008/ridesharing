package com.example.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Config {
    @Bean()
    @Order(1)
    public SecurityFilterChain publicApiSecurityFilterChain(HttpSecurity http) throws Exception{
        http
        .securityMatcher("/api/test") 
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());  
        

    return http.build();
    }

    @Bean
     @Order(2)
    public SecurityFilterChain privatApiSecurityFilterChain(HttpSecurity http) throws Exception{
        http
      .securityMatcher("/api/**")
      .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
      .csrf(csrf -> csrf.disable());
      
    return http.build();

    
    }


    
}
