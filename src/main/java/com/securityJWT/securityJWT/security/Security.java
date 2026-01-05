package com.securityJWT.securityJWT.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
public class Security {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(configure->configure.requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/students/**").hasAnyRole("STUDENT","TEACHER","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/students").hasAnyRole("STUDENT","TEACHER","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/students/**").hasAnyRole("TEACHER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/students/**").hasAnyRole("TEACHER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/students/**").hasRole("ADMIN")

                );
        return http.build();


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
