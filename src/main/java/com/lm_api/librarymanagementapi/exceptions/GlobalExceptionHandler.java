package com.lm_api.librarymanagementapi.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionResponse exceptionResponse;

    @Autowired
    public GlobalExceptionHandler(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> bookNotFound(
            BookNotFoundException ex, WebRequest webRequest) {
        return exceptionResponse.exceptionResponse(
                ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> userNotFound(
            UserNotFoundException ex, WebRequest webRequest) {
        return exceptionResponse.exceptionResponse(
                ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<Map<String, Object>> loanNotFound(
            LoanNotFoundException ex, WebRequest webRequest) {
        return exceptionResponse.exceptionResponse(
                ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> categoryNotFound(
            CategoryNotFoundException ex, WebRequest webRequest) {
        return exceptionResponse.exceptionResponse(
                ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> usernameNotFound(
            UsernameNotFoundException ex, WebRequest webRequest) {
        return exceptionResponse.exceptionResponse(
                ex, webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotFoundException(
            MethodArgumentNotValidException ex, WebRequest webRequest) {

        Map<String, Object> map = new HashMap<>();
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + " : " + error.getDefaultMessage());
        }

        map.put("timestamp", LocalDateTime.now());
        map.put("message", "validation failed");
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("errors", errors);
        map.put("path", webRequest.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> globalExceptions(
            Exception ex, WebRequest webRequest) {
        return exceptionResponse.exceptionResponse(
                ex, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}