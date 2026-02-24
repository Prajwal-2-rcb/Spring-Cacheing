package com.codesnippet.weather_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Minimal Basic Authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.httpBasic(withDefaults());// it will allow for all endpoints so write below code
        http.authorizeHttpRequests(auth->
                auth.anyRequest().authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }

}
