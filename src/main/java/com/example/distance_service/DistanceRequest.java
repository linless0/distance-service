package com.example.distance_service;

public class DistanceRequest {

    private String fromCity;
    private String toCity;

    // Пустой конструктор нужен Spring для JSON-десериализации
    public DistanceRequest() {
    }

    public DistanceRequest(String fromCity, String toCity) {
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }
}