package com.example.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class Security {

  private final Jwtauth jwtauth;

  public Security(Jwtauth jwtauth) {
    this.jwtauth = jwtauth;
  }

  @Bean
  @Order(1)
  public SecurityFilterChain publicApiSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/api/health")
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain privateApiSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/api/**")
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .csrf(csrf -> csrf.disable())
        .addFilterBefore(jwtauth, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
