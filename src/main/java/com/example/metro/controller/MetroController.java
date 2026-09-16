package com.example.metro.controller;

import com.example.metro.model.*;
import com.example.metro.service.MetroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MetroController {
    private final MetroService service;

    public MetroController(MetroService service) {
        this.service = service;
    }

    // 1. GET /api/stations
    @GetMapping("/stations")
    public List<Station> stations(
            @RequestParam(required = false) String line,
            @RequestParam(required = false) String q) {
        return service.allStations(line, q);
    }

    // 2. GET /api/stations/{id}
    @GetMapping("/stations/{id}")
    public Station station(@PathVariable long id) {
        return service.station(id);
    }

    // 3. POST /api/stations
    @PostMapping("/stations")
    @ResponseStatus(HttpStatus.CREATED)
    public Station createStation(@Valid @RequestBody Station station) {
        return service.createStation(station);
    }

    // 4. PUT /api/stations/{id}
    @PutMapping("/stations/{id}")
    public Station updateStation(@PathVariable long id, @Valid @RequestBody Station station) {
        return service.updateStation(id, station);
    }

    // 5. DELETE /api/stations/{id}
    @DeleteMapping("/stations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStation(@PathVariable long id) {
        service.deleteStation(id);
    }

    // 6. GET /api/lines
    @GetMapping("/lines")
    public List<MetroLine> lines() {
        return service.allLines();
    }

    // 7. GET /api/lines/{id}
    @GetMapping("/lines/{id}")
    public MetroLine line(@PathVariable String id) {
        return service.line(id);
    }

    // 8. GET /api/stations/{id}/arrivals
    @GetMapping("/stations/{id}/arrivals")
    public List<Arrival> arrivals(@PathVariable long id) {
        return service.arrivals(id);
    }

    // 9. GET /api/status
    @GetMapping("/status")
    public MetroStatus status() {
        return service.status();
    }

    // 10. GET /api/health
    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("UP");
    }

    // 11. GET /api/stations/{id}/lines
    @GetMapping("/stations/{id}/lines")
    public List<MetroLine> stationLines(@PathVariable long id) {
        Station station = service.station(id);
        return station.lines().stream().map(service::line).toList();
    }

    // 12. GET /api/stations/search?q=...
    @GetMapping("/stations/search")
    public List<Station> search(@RequestParam String q) {
        return service.allStations(null, q);
    }

    public record HealthResponse(String status) {}
}
