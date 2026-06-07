# Distance Service

A REST API service built with Java and Spring Boot for calculating
distances between geographic coordinates.

## Technologies
- Java 26
- Spring Boot
- Maven
- H2 Database / JPA
- REST API

## How to run
```bash
mvn spring-boot:run
```
OR get .jar snapshot file from distance-service/COMPILED/

## Endpoints
- `GET /distance?from=...&to=...` — returns distance in km
