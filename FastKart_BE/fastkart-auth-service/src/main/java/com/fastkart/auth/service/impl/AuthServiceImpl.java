package com.fastkart.auth.service.impl;

import com.fastkart.auth.config.JwtService;
import com.fastkart.auth.constants.CommonConstants;
import com.fastkart.auth.entity.UserInfo;
import com.fastkart.auth.exception.AuthException;
import com.fastkart.auth.models.AddUserRequest;
import com.fastkart.auth.models.AuthRequest;
import com.fastkart.auth.models.AuthResponse;
import com.fastkart.auth.models.LoginResponse;
import com.fastkart.auth.repository.UserRespository;
import com.fastkart.auth.service.AuthService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse saveUser(AddUserRequest addUserRequest) {
        Optional<UserInfo> user = userRespository.findByUserName(addUserRequest.getUserName());
        if (user.isPresent()) {
            throw new AuthException(CommonConstants.USER_ALREADY_EXIST);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(addUserRequest.getUserName());
        userInfo.setName(addUserRequest.getName());
        userInfo.setPassword(passwordEncoder.encode(addUserRequest.getPassword()));
        userInfo.setRole(addUserRequest.getRole().toUpperCase(Locale.ROOT));
        userRespository.save(userInfo);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setResponseMessage(CommonConstants.SUCCESS);
        return authResponse;
    }

    public LoginResponse generateToken(AuthRequest authRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            Optional<UserInfo> userInfo = userRespository.findByUserName(authRequest.getUserName());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            loginResponse.setAccessToken(jwtService.generateToken(userInfo.get()));
        } catch (Exception ex) {
            throw new AuthException(CommonConstants.INVALID_CREDENTIAL);
        }
        return loginResponse;
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
