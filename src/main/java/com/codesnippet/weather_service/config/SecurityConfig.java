package com.codesnippet.weather_service.config;

import com.codesnippet.weather_service.entity.Permissions;
import com.codesnippet.weather_service.entity.Role;
import com.codesnippet.weather_service.filters.JWTAuthFilter;
import com.codesnippet.weather_service.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //Minimal Basic Authentication

    @Autowired
    JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.httpBasic(withDefaults());// it will allow for all endpoints so write below code

        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->
                auth.requestMatchers("/authenticate").permitAll()
//                        .requestMatchers("/weather/health").hasRole("ADMIN")
//                        .requestMatchers("/weather/health").hasRole(Role.ADMIN.name())
                        //.requestMatchers(HttpMethod.GET, "/weather/**").hasAnyAuthority(Permissions.WEATHER_READ.name())
                       // .requestMatchers(HttpMethod.POST,"/weather/**").hasAnyAuthority(Permissions.WEATHER_WRITE.name())
                       // .requestMatchers(HttpMethod.DELETE,"/weather/**").hasAnyAuthority(Permissions.WEATHER_DELETE.name())
                        . anyRequest().authenticated());
//                .httpBasic(withDefaults());// Remove Basic Authentication filter

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}
