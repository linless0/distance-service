package com.example.distance_service;

// Простой класс для представления ошибки в JSON
public class ErrorResponse {

    // HTTP статус (например, 400 или 500)
    private int status;

    // Краткое сообщение об ошибке
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Геттеры и сеттеры нужны, чтобы Spring смог превратить объект в JSON
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}