package com.vehiclemanager.vehiclemanager.controller;

import com.vehiclemanager.vehiclemanager.dto.LoginRequest;
import com.vehiclemanager.vehiclemanager.dto.LoginResponse;
import com.vehiclemanager.vehiclemanager.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "Generate JWT token", description = "Generates a JWT token based on the provided login credentials", tags = "Token Endpoint")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String jwtValue = tokenService.generateToken(loginRequest);
            return ResponseEntity.ok(new LoginResponse(jwtValue, 300L));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("User or password is invalid!");
        }
    }
}
