package com.kakao.borrowme._core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf().disable();

        http.formLogin().loginProcessingUrl("login");

        http.authorizeRequests(authorize -> authorize.antMatchers("/").authenticated().anyRequest().permitAll());

        return http.build();
    }
}
