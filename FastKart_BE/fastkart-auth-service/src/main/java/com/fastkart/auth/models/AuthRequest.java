package com.fastkart.auth.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = "User Name is mandatory")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    private String password;

}
