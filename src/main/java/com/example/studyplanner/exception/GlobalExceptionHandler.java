package com.example.studyplanner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return createResponse("VALIDATION_ERROR", "Données invalides", errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(ResourceNotFoundException ex) {
        return createResponse("NOT_FOUND", ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, Object> handleBusiness(BusinessException ex) {
        return createResponse("BUSINESS_ERROR", ex.getMessage(), null, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private Map<String, Object> createResponse(String code, String message, Map<String, String> errors, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        if (errors != null) response.put("errors", errors);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}