package com.klack.klack.controller;

import com.klack.klack.dto.AuthResponse;
import com.klack.klack.dto.LoginRequest;
import com.klack.klack.dto.RegisterRequest;
import com.klack.klack.entity.Users;
import com.klack.klack.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
        // Extract token from Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }

        String token = authHeader.substring(7); // remove "Bearer "

        Users user = authService.validateAndGetUser(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        return ResponseEntity.ok(user);
    }


}
