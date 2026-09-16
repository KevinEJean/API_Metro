package com.example.metro.model;

public record Arrival(
        Long stationId,
        String lineId,
        String destination,
        int minutes,
        String status
) {}
