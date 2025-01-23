package com.mysql.security;

import com.mysql.config.CorsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/auth/**",
                        "/cliente/**", "/auth/logar", "/email/**").permitAll())

                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/login", "/css/**",
                        "/js/**", "/cadastrar", "/v3/api-docs/**", "/swagger-ui/**", "docker").permitAll())

                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/atendimentos/**", "/cliente/**",
                        "/profissional/**").authenticated())

                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.PUT, "/atendimentos/**", "/auth/**").authenticated())

                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/auth/**", "/profissional/**", "/cliente/**", "/atendimentos/**").authenticated())

                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE, "/auth/**", "/cliente/**", "/profissional/**", "/atendimentos/**").authenticated())

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                
                .build();


    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}


