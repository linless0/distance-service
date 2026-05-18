package com.example.distance_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Обработка ошибок валидации
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        
        log.warn("Validation error: {}", ex.getMessage());
        // Создаём тело ответа с кодом 400 и текстом ошибки
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        // Возвращаем JSON + статус 400
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Обработка любых других unchecked ошибок (NullPointerException и т.п.)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        log.error("Internal server error", ex);

        // Внутренняя ошибка сервера
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}