package com.fastkart.auth.service;

import com.fastkart.auth.models.AddUserRequest;
import com.fastkart.auth.models.AuthRequest;
import com.fastkart.auth.models.AuthResponse;
import com.fastkart.auth.models.LoginResponse;

public interface AuthService {

    AuthResponse saveUser(AddUserRequest addUserRequest);

    LoginResponse generateToken(AuthRequest authRequest);
}
