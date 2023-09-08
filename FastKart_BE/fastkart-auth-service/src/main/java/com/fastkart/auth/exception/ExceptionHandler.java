package com.fastkart.auth.exception;

import com.fastkart.auth.constants.CommonConstants;
import com.fastkart.auth.models.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthException.class)
    protected ResponseEntity<Object> handleAuthException(AuthException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST, CommonConstants.VALIDATION_ERROR, errorList);
        return new ResponseEntity<Object>(errorDetails, errorDetails.getStatus());
    }
}
