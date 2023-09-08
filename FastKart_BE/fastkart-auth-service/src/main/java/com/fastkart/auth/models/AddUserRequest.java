package com.fastkart.auth.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {

    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{5,29}$", message = "UserName must start with Alphabet and does not contain special character")
    @NotBlank(message = "User Name is mandatory")
    private String userName;
    @Pattern(regexp = "^[a-zA-Z\\s]+", message = "Name Validation failed")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be of atleast 8 characters, atleast one uppercase, lowercase, number and special character")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Role is mandatory")
    private String role;
}
