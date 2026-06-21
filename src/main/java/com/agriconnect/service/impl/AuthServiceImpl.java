package com.agriconnect.service.impl;


import com.agriconnect.dto.AuthResponse;
import com.agriconnect.dto.LoginRequest;
import com.agriconnect.dto.RegisterRequest;
import com.agriconnect.entity.Role;
import com.agriconnect.entity.User;
import com.agriconnect.repository.RoleRepository;
import com.agriconnect.repository.UserRepository;
import com.agriconnect.service.AuthService;
import com.agriconnect.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        Role role = roleRepository.findByName(
                        request.getRole().toUpperCase())
                .orElseThrow(() ->
                        new RuntimeException("Invalid role"));

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(
                        passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        var userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities(
                                "ROLE_" + role.getName())
                        .build();

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        var userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities(
                                "ROLE_" + user.getRole().getName())
                        .build();

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
