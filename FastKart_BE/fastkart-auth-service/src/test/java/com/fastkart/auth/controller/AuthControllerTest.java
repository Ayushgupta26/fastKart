package com.fastkart.auth.controller;

import com.fastkart.auth.models.AddUserRequest;
import com.fastkart.auth.models.AuthRequest;
import com.fastkart.auth.models.AuthResponse;
import com.fastkart.auth.models.LoginResponse;
import com.fastkart.auth.service.impl.AuthServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthServiceImpl authService;

    @InjectMocks
    AuthController authController;

    @Test
    void saveUserTest(){
        Mockito.when(authService.saveUser(any())).thenReturn(new AuthResponse());
        assertNotNull(authController.saveUser(new AddUserRequest()));
    }

    @Test
    void authenticateAndGetTokenTest(){
        Mockito.when(authService.generateToken(any())).thenReturn(new LoginResponse());
        assertNotNull(authController.authenticateAndGetToken(new AuthRequest()));
    }

}
