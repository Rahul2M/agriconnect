package com.agriconnect.service;



import com.agriconnect.dto.AuthResponse;
import com.agriconnect.dto.LoginRequest;
import com.agriconnect.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
