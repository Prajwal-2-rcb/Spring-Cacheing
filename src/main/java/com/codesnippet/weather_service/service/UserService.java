package com.codesnippet.weather_service.service;

import com.codesnippet.weather_service.entity.RegisterUserRequest;
import com.codesnippet.weather_service.entity.UserResponse;
import com.codesnippet.weather_service.entity.Users;
import com.codesnippet.weather_service.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsRepository userDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        //TODO check if user is already present
        //TODO encode password in request
        //TODO Save user
        if(userDetailsRepository.findByUsername(registerUserRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already in use");

        }
        Users users=new Users();
        users.setUsername(registerUserRequest.getUsername());
        users.setRole(registerUserRequest.getRole());
        users.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        Users savedUser= userDetailsRepository.save(users);
        return new UserResponse(savedUser.getId(),savedUser.getUsername(),savedUser.getRole().name());


    }
}
