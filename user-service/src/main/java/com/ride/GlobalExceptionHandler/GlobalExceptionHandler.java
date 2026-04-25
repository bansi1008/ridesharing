package com.ride.GlobalExceptionHandler;

import com.ride.CustomException.AppException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // @ExceptionHandler(EmailAlready.class)
    // public ResponseEntity<?> handleEmailAlready(EmailAlready ex) {
    // Map<String, Object> error = new HashMap<>();
    // error.put("message", ex.getMessage());
    // error.put("status", 409);

    // return ResponseEntity.status(409).body(error);
    // }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", ex.getStatus().value());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonParseError(HttpMessageNotReadableException ex) {

        Throwable cause = ex.getCause();

        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }

        String message = "Invalid request";

        if (cause instanceof IllegalArgumentException) {
            message = cause.getMessage();
        }
        Map<String, String> error = new HashMap<>();
        error.put("message", message.toString());
        error.put("status", "400");
        return ResponseEntity.badRequest().body(error);
    }

}
