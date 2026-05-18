package com.example.distance_service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GeoDistanceService {

    private static final Logger log = LoggerFactory.getLogger(GeoDistanceService.class);

    private static final String NOMINATIM_URL =
            "https://nominatim.openstreetmap.org/search";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    static class CityCoordinates {
        private final double lat;
        private final double lon;

        CityCoordinates(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        double getLat() { return lat; }
        double getLon() { return lon; }
    }

    public double getDistanceInKm(String fromCity, String toCity) {
        CityCoordinates from = fetchCoordinatesFromNominatim(fromCity);
        CityCoordinates to   = fetchCoordinatesFromNominatim(toCity);
        return haversine(from.getLat(), from.getLon(), to.getLat(), to.getLon());
    }

    private CityCoordinates fetchCoordinatesFromNominatim(String cityName) {
         try {
        // ЛИМИТЫ NOMINASIM
        Thread.sleep(1000);

        String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        String url = NOMINATIM_URL
                + "?q=" + encodedCity
                + "&format=json"
                + "&limit=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "distance-service/1.0 (your_email@example.com)")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        String body = response.body();
        log.info("Nominatim response for '{}': {}", cityName, body);
        return parseLatLon(body);
    } catch (Exception e) {
        throw new RuntimeException("Failed to fetch coordinates for city: " + cityName, e);
    }
}

    private CityCoordinates parseLatLon(String body) {
        if (body == null || body.length() == 0 || body.equals("[]")) {
            throw new IllegalArgumentException("City not found in Nominatim response");
        }

        int latIndex = body.indexOf("\"lat\":\"");
        int lonIndex = body.indexOf("\"lon\":\"");

        if (latIndex == -1 || lonIndex == -1) {
            throw new IllegalArgumentException("lat/lon not found in Nominatim response");
        }

        int latValueStart = latIndex + "\"lat\":\"".length();
        int latValueEnd = body.indexOf("\"", latValueStart);
        String latStr = body.substring(latValueStart, latValueEnd);

        int lonValueStart = lonIndex + "\"lon\":\"".length();
        int lonValueEnd = body.indexOf("\"", lonValueStart);
        String lonStr = body.substring(lonValueStart, lonValueEnd);

        double lat = Double.parseDouble(latStr);
        double lon = Double.parseDouble(lonStr);

        return new CityCoordinates(lat, lon);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0; // км
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double rLat1 = Math.toRadians(lat1);
        double rLat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(rLat1) * Math.cos(rLat2) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}