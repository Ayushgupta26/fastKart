package com.fastkart.auth.controller;

import com.fastkart.auth.models.AddUserRequest;
import com.fastkart.auth.models.AuthRequest;
import com.fastkart.auth.models.AuthResponse;
import com.fastkart.auth.models.LoginResponse;
import com.fastkart.auth.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> saveUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        return new ResponseEntity<AuthResponse>(authService.saveUser(addUserRequest), HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<LoginResponse> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        LoginResponse loginResponse = authService.generateToken(authRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
