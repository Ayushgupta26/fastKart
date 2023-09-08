package com.fastkart.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private HttpStatus status;
    private String errorMessage;

    public ErrorResponse(String errorMessage, HttpStatus status) {
        super();
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
