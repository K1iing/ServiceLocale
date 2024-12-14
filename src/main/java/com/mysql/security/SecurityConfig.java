package com.mysql.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService ConfiguracaoSeguranca() {
        UserDetails usuario1 = User.builder()
                .username("joao@gmail.com")
                .password("{noop}joao123")
                .build();

        UserDetails usuario2 = User.builder()
                .username("maria@gmail.com")
                .password("{noop}maria123")
                .build();

                return new InMemoryUserDetailsManager(usuario1, usuario2);
    }

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return  http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "/assets/**").permitAll();

                    req.requestMatchers("/home", "/atendimentoshome").authenticated();

                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/login")// Página personalizada de login
                        .defaultSuccessUrl("/home", true) //
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
                .rememberMe(rememberMe -> rememberMe.key("lembrarDeMim").alwaysRemember(true))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login");
                        }))
                .csrf(Customizer.withDefaults())
                .build();
    }


}


