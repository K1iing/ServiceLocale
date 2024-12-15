package com.mysql.security;

import com.mysql.model.client.Cliente;
import com.mysql.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private ClienteRepository clienteRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Buscando o cliente pelo email
            Cliente cliente = clienteRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));

            // O Spring Security irá verificar a senha usando o PasswordEncoder automaticamente
            return new org.springframework.security.core.userdetails.User(
                    cliente.getEmail(),
                    cliente.getSenha(), // Senha criptografada no banco de dados
                    cliente.getAuthorities()
            );
        };
    }


    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return  http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "/assets/**", "/cliente/**",
                            "/atendimentos/**", "/profissional/**", "/cliente/cadastrar", "/cadastrar/**").permitAll();

                    req.requestMatchers("/home", "/atendimentoshome").authenticated();

                    req.anyRequest().permitAll();
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
                .csrf(csrf -> csrf.disable())
                .build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


