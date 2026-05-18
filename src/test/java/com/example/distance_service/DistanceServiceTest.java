package com.example.distance_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class DistanceServiceTest {

    // Создаём экземпляры зависимостей напрямую (без Spring контекста)
    private final DistanceCache distanceCache = new DistanceCache();
    private final RequestCounter requestCounter = new RequestCounter();
    private final DistanceResultRepository resultRepository = null;

    // подставной GeoDistanceService только для теста
    private final GeoDistanceService fakeGeoService = new GeoDistanceService() {
        @Override
        public double getDistanceInKm(String fromCity, String toCity) {
            if (fromCity.equals("Madrid") && toCity.equals("Lisbon")) {
                return 502.9883215654312;
            }
            throw new IllegalArgumentException("Unexpected route in test");
        }
    };

private final DistanceService distanceService =
        new DistanceService(distanceCache, requestCounter, resultRepository, fakeGeoService);
    @Test
    public void testCalculateDistance_MadridLisbon() {
    DistanceResponse response = distanceService.calculateDistance("Madrid", "Lisbon");

    assertNotNull(response);
    assertEquals("Madrid", response.getFrom());
    assertEquals("Lisbon", response.getTo());
    assertEquals(502.9883215654312, response.getDistance());
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