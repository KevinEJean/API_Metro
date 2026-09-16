package com.example.metro.service;

import com.example.metro.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MetroService {
    private final Map<Long, Station> stations = new ConcurrentHashMap<>();
    private final Map<String, MetroLine> lines = new ConcurrentHashMap<>();
    private final AtomicLong stationIds = new AtomicLong(3);

    public MetroService() {
        stations.put(1L, new Station(1L, "Central", "CEN", List.of("red", "blue"), 45.5017, -73.5673));
        stations.put(2L, new Station(2L, "Old Port", "OLD", List.of("blue"), 45.5075, -73.5538));
        stations.put(3L, new Station(3L, "University", "UNI", List.of("red"), 45.5048, -73.5772));

        lines.put("red", new MetroLine("red", "Red Line", "#D32F2F", List.of(1L, 3L)));
        lines.put("blue", new MetroLine("blue", "Blue Line", "#1976D2", List.of(1L, 2L)));
    }

    public List<Station> allStations(String line, String q) {
        return stations.values().stream()
                .filter(s -> line == null || s.lines().stream().anyMatch(l -> l.equalsIgnoreCase(line)))
                .filter(s -> q == null || s.name().toLowerCase().contains(q.toLowerCase())
                        || s.code().toLowerCase().contains(q.toLowerCase()))
                .sorted(Comparator.comparing(Station::id))
                .toList();
    }

    public Station station(long id) {
        return Optional.ofNullable(stations.get(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Station not found"));
    }

    public Station createStation(Station input) {
        long id = stationIds.incrementAndGet();
        Station created = new Station(id, input.name(), input.code(), input.lines(),
                input.latitude(), input.longitude());
        stations.put(id, created);
        return created;
    }

    public Station updateStation(long id, Station input) {
        station(id);
        Station updated = new Station(id, input.name(), input.code(), input.lines(),
                input.latitude(), input.longitude());
        stations.put(id, updated);
        return updated;
    }

    public void deleteStation(long id) {
        station(id);
        stations.remove(id);
    }

    public List<MetroLine> allLines() {
        return lines.values().stream().sorted(Comparator.comparing(MetroLine::id)).toList();
    }

    public MetroLine line(String id) {
        return Optional.ofNullable(lines.get(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Line not found"));
    }

    public List<Arrival> arrivals(long stationId) {
        station(stationId);
        List<Arrival> result = new ArrayList<>();
        Station s = station(stationId);
        for (String line : s.lines()) {
            result.add(new Arrival(stationId, line, line.equals("red") ? "University" : "Old Port",
                    line.equals("red") ? 3 : 6, "ON_TIME"));
            result.add(new Arrival(stationId, line, line.equals("red") ? "Central" : "Central",
                    line.equals("red") ? 9 : 12, "ON_TIME"));
        }
        return result;
    }

    public MetroStatus status() {
        return new MetroStatus("OPERATIONAL", Instant.now(), List.of(
                new MetroStatus.LineStatus("red", "NORMAL", "Service running normally"),
                new MetroStatus.LineStatus("blue", "NORMAL", "Service running normally")
        ));
    }
}
