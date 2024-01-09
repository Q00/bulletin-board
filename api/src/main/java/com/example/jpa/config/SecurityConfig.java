package com.example.jpa.config;

import com.example.jpa.common.guard.JwtAuthenticationFilter;
import com.example.jpa.common.guard.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableConfigurationProperties(SecurityProps.class)
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityProps securityProps;
    private final JwtTokenProvider jwtTokenProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(configurer -> {
                    configurer.configurationSource(getCorsSource());
                })
                .csrf(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private UrlBasedCorsConfigurationSource getCorsSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(securityProps.getCors().getAllowedOrigins());
        configuration.setAllowedMethods(securityProps.getCors().getAllowedMethods());
        configuration.setAllowCredentials(securityProps.getCors().isAllowCredentials());
        configuration.setAllowedHeaders(securityProps.getCors().getAllowedHeaders());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
