package com.example.metro.model;

import java.util.List;

public record MetroLine(
        String id,
        String name,
        String color,
        List<Long> stationIds
) {}
