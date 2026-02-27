package com.codesnippet.weather_service.service;

import com.codesnippet.weather_service.entity.Role;
import com.codesnippet.weather_service.entity.Users;
import com.codesnippet.weather_service.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userDetailsRepository.findByUsername("admin").isEmpty()) {
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                admin.setRole(Role.ROLE_ADMIN);
                userDetailsRepository.save(admin);
                System.out.println("Admin user created");
            }
            if (userDetailsRepository.findByUsername("user").isEmpty()) {
                Users admin = new Users();
                admin.setUsername("user");
                admin.setPassword(passwordEncoder.encode("user1234"));
                admin.setRole(Role.ROLE_USER);
                userDetailsRepository.save(admin);
                System.out.println("User user created");
            }
        };

    }

}
