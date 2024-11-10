package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer; // 추가된 import
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer; // 추가된 import
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer; // 추가된 import
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer; // 추가된 import
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
@Configuration
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(final @NotNull HttpSecurity http) throws Exception {
        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/", "/auth/**").permitAll() // '/' 및 '/auth/**' 경로는 인증 없이 접근 가능
                                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
        return http.build();  // SecurityFilterChain 반환
    }
}
