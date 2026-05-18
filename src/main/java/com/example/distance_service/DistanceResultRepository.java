package com.example.distance_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistanceResultRepository extends JpaRepository<DistanceResult, Long> {
}

//JpaRepository даёт готовые методы save, findAll, findById, и т.п.