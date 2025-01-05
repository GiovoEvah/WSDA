package com.example.square.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityController {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disabilita protezione CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/styles/**",   // File di stile (CSS)
                                "/img/**",      // File immagine
                                "/",            // Endpoint di login
                                "/postspage",   // Postspage
                                "/register", // Endpoint di registrazione
                                "/registerForm",// Form di registrazione
                                "/loginForm"    // Form di login
                        ).permitAll() // Consenti accesso pubblico
                        .anyRequest().authenticated() // Altri endpoint richiedono autenticazione
                )
                .formLogin(login -> login
                        .loginPage("/") // Specifica il tuo endpoint personalizzato
                        .loginProcessingUrl("/loginForm") // URL per processare il form
                        .defaultSuccessUrl("/postspage", true) // Reindirizza dopo un login riuscito
                        .failureUrl("/login?error=true") // Reindirizza in caso di errore
                        .permitAll()
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
        ;
        return http.build();
    }
}
