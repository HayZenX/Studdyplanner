package com.example.studyplanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public org.springframework.security.web.SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new org.springframework.security.web.util.matcher.AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new org.springframework.security.web.util.matcher.AntPathRequestMatcher("/actuator/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .httpBasic(org.springframework.security.config.Customizer.withDefaults())
                .formLogin(org.springframework.security.config.Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        org.springframework.security.core.userdetails.UserDetails alice = org.springframework.security.core.userdetails.User.builder()
                .username("alice")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        org.springframework.security.core.userdetails.UserDetails bob = org.springframework.security.core.userdetails.User.builder()
                .username("bob")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new org.springframework.security.provisioning.InMemoryUserDetailsManager(alice, bob);
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}