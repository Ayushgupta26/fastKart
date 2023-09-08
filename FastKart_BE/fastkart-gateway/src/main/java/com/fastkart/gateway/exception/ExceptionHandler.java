package com.fastkart.gateway.exception;

import com.fastkart.gateway.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UnauthorisedException.class)
    protected ResponseEntity<Object> handleUnauthorisedException(Exception ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<Object>(error, HttpStatus.UNAUTHORIZED);
    }
}
