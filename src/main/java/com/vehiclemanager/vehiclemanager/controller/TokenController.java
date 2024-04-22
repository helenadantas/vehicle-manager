package com.vehiclemanager.vehiclemanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.vehiclemanager.vehiclemanager.dto.LoginRequest;
import com.vehiclemanager.vehiclemanager.dto.LoginResponse;
import com.vehiclemanager.vehiclemanager.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "Token Management", description = "Endpoints for token management")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "Generate JWT token", notes = "Generates a JWT token based on the provided login credentials")
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
