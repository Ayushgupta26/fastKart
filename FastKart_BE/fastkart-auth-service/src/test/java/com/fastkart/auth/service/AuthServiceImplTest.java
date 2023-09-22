package com.fastkart.auth.service;

import com.fastkart.auth.config.JwtService;
import com.fastkart.auth.entity.UserInfo;
import com.fastkart.auth.exception.AuthException;
import com.fastkart.auth.models.AddUserRequest;
import com.fastkart.auth.models.AuthRequest;
import com.fastkart.auth.repository.UserRespository;
import com.fastkart.auth.service.impl.AuthServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRespository userRespository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void saveUserTest_success(){
        Mockito.when(passwordEncoder.encode(any())).thenReturn("encoded");
        Mockito.when(userRespository.findByUserName(any())).thenReturn(Optional.empty());
        Mockito.when(userRespository.save(any())).thenReturn(new UserInfo());
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setRole("SELLER");
        assertNotNull(authService.saveUser(addUserRequest));
    }

    @Test
    void saveUserTest_exception(){
        Mockito.when(userRespository.findByUserName(any())).thenReturn(Optional.of(new UserInfo()));
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setRole("SELLER");
        assertThrows(AuthException.class, () ->authService.saveUser(addUserRequest));
    }

    @Test
    void generateTokenTest_success(){
        Mockito.when(userRespository.findByUserName(any())).thenReturn(Optional.of(new UserInfo()));
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(authentication);
        Mockito.when(jwtService.generateToken(any())).thenReturn("testAccessToken");
        assertNotNull(authService.generateToken(new AuthRequest()));
    }

    @Test
    void generateTokenTest_exception(){
        Mockito.when(userRespository.findByUserName(any())).thenReturn(Optional.of(new UserInfo()));
        Mockito.when(authenticationManager.authenticate(any())).thenThrow(new AuthException());
        assertThrows(AuthException.class, () ->authService.generateToken(new AuthRequest()));
    }

    @Test
    void validateToken_success(){
        doNothing().when(jwtService).validateToken(any());
        assertNotNull(authService.validateToken("tokenTest"));
    }
}
