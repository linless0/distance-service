package com.example.distance_service;

import org.springframework.stereotype.Component;

@Component
public class RequestCounter {

    private int count = 0;

    // Увеличиваем счётчик на 1
    public synchronized void increment() {
        count++;
    }

    // Получаем текущее значение
    public synchronized int getCount() {
        return count;
    }
}