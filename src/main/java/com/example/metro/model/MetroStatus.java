package com.example.metro.model;

import java.time.Instant;
import java.util.List;

public record MetroStatus(
        String networkStatus,
        Instant updatedAt,
        List<LineStatus> lines
) {
    public record LineStatus(String lineId, String status, String message) {}
}
