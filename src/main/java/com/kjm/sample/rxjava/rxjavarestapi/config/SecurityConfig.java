package com.kjm.sample.rxjava.rxjavarestapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests()
                .requestMatchers(
                        new MvcRequestMatcher(introspector,
                                "/swagger-ui/index.html"),
                        new MvcRequestMatcher(introspector,
                                "/v3/api-docs"))
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .headers(headers -> headers.frameOptions().sameOrigin())
                // .csrf(csrf -> csrf.ignoringRequestMatchers(new
                // MvcRequestMatcher(introspector, "/h2-console/**"))) // ?????
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
