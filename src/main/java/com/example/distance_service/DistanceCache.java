package com.example.distance_service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Простейший in-memory кэш расстояний
@Component
public class DistanceCache {

    // Map в памяти: ключ -> расстояние
    // Здесь ключом будет строка вида "Minsk->Moscow"
    private final Map<String, Double> cache = new HashMap<>();

    // Получить расстояние из кэша (или null, если записи нет)
    public Double getDistance(String fromCity, String toCity) {
        String key = buildKey(fromCity, toCity);
        return cache.get(key);
    }

    // Сохранить расстояние в кэш
    public void putDistance(String fromCity, String toCity, double distance) {
        String key = buildKey(fromCity, toCity);
        cache.put(key, distance);
    }

    // Формируем строковый ключ для Map
    private String buildKey(String fromCity, String toCity) {
        return fromCity + "->" + toCity;
    }
}