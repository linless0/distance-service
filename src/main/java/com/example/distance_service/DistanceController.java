package com.example.distance_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
public class DistanceController {

    private final DistanceService distanceService;
    private final RequestCounter requestCounter;
    private final DistanceResultRepository distanceResultRepository; // новое поле

    public DistanceController(DistanceService distanceService,
                              RequestCounter requestCounter,
                              DistanceResultRepository distanceResultRepository) {
        this.distanceService = distanceService;
        this.requestCounter = requestCounter;
        this.distanceResultRepository = distanceResultRepository; // присваиваем
    }

    @GetMapping("/metrics/requests")
    public int getRequestsCount() {
        return requestCounter.getCount();
    }

    @GetMapping("/distance")
    public DistanceResponse getDistance(
            @RequestParam("fromCity") String fromCity,
            @RequestParam("toCity") String toCity
    ) {
        return distanceService.calculateDistance(fromCity, toCity);
    }

    @GetMapping("/distance/results")
    public List<DistanceResult> getAllResults() {
        return distanceResultRepository.findAll();
    }

    @PostMapping("/distance/bulk")
    public List<DistanceResponse> getDistancesBulk(
            @RequestBody List<DistanceRequest> requests
    ) {
        return distanceService.calculateDistances(requests);
    }
}