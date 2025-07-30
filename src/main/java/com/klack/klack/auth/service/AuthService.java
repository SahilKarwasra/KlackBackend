package com.klack.klack.auth.service;

import com.klack.klack.config.JwtUtils;
import com.klack.klack.auth.dto.AuthResponse;
import com.klack.klack.auth.dto.LoginRequest;
import com.klack.klack.auth.dto.RegisterRequest;
import com.klack.klack.auth.entity.Users;
import com.klack.klack.auth.enums.Role;
import com.klack.klack.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest registerRequest) {
        System.out.println("AuthService: Starting registration for email: " + registerRequest.getEmail());
        
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            System.out.println("AuthService: Email already exists: " + registerRequest.getEmail());
            throw new RuntimeException("Email already exists");
        }

        System.out.println("AuthService: Creating new user");
        Users user = Users.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        
        System.out.println("AuthService: Saving user to database");
        userRepository.save(user);

        System.out.println("AuthService: Generating JWT token");
        String token = jwtUtils.generateToken(user);
        
        System.out.println("AuthService: Registration completed successfully");
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        System.out.println("AuthService: Starting login for email: " + loginRequest.getEmail());
        
        Users user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("AuthService: Password mismatch for email: " + loginRequest.getEmail());
            throw new RuntimeException("Passwords don't match");
        }
        
        System.out.println("AuthService: Login successful, generating token");
        String token = jwtUtils.generateToken(user);
        return new AuthResponse(token);
    }

    public Users validateAndGetUser(String token) {
        System.out.println("AuthService: Validating user");
        String getEmail = jwtUtils.getEmailFromToken(token);

        if(getEmail == null) {
            System.out.println("AuthService: Invalid token");
            throw new RuntimeException("Invalid token");
        }

        return  userRepository.findByEmail(getEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}












