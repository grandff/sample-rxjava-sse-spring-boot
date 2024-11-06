package com.kjm.sample.rxjava.rxjavarestapi.config;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.kjm.sample.rxjava.rxjavarestapi.config.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {    
        
    /**
     * CORS 설정
     * - 허용할 오리진, 메서드, 헤더 등을 설정
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // 1시간

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /**
     * 패스워드 인코더 설정
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인메모리 사용자 설정
     * 실제 운영 환경에서는 데이터베이스 기반의 사용자 관리를 구현해야 함
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("swagger")
                .password(passwordEncoder().encode("swagger"))
                .roles("SWAGGER_ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


    /**
     * Spring Security 필터 체인 설정
     * - Swagger UI 및 API 문서에 대한 인증 요구
     * - 나머지 요청은 모두 허용
     * - CSRF 비활성화 (REST API를 위한 설정)
     * - X-Frame-Options 설정으로 동일 출처 프레임 허용
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, 
                                         HandlerMappingIntrospector introspector,
                                         JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        MvcRequestMatcher swaggerUiMatcher = new MvcRequestMatcher(introspector, "/swagger-ui/index.html");
        MvcRequestMatcher apiDocsMatcher = new MvcRequestMatcher(introspector, "/v3/api-docs");

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(swaggerUiMatcher, apiDocsMatcher).authenticated()
                            .anyRequest().permitAll();
                })
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
