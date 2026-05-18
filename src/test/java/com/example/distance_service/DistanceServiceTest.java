package com.example.distance_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DistanceServiceTest {

    // Создаём экземпляры зависимостей напрямую (без Spring контекста)
    private final DistanceCache distanceCache = new DistanceCache();
    private final RequestCounter requestCounter = new RequestCounter();

    // В тесте используем упрощённый конструктор DistanceService без репозитория
    private final DistanceService distanceService =
            new DistanceService(distanceCache, requestCounter,null);

    // Тест "happy path": корректные города -> успешный ответ
    @Test
    public void testCalculateDistance_ValidCities() {
        // Вызываем метод с корректными названиями
        DistanceResponse response = distanceService.calculateDistance("Minsk", "Moscow");

        // Проверяем, что ответ не null
        assertNotNull(response);

        // Проверяем, что названия городов сохранились правильно
        assertEquals("Minsk", response.getFrom());
        assertEquals("Moscow", response.getTo());

        // Проверяем, что расстояние такое, как в бизнес-логике (700.0)
        assertEquals(700.0, response.getDistance());
    }

    // Тест валидации: некорректное название города -> IllegalArgumentException
    @Test
    public void testCalculateDistance_InvalidCityName() {
        // Проверяем, что при некорректном названии города выбрасывается нужное исключение
        assertThrows(IllegalArgumentException.class, () -> {
            distanceService.calculateDistance("Mi1nsk", "Moscow");
        });
    }
}