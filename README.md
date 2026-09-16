# Metro API — Spring Boot

A small RESTful Metro/transit API with **12 endpoints**, validation, HTTP status handling, and in-memory sample data.

## Requirements
- Java 17+
- Maven 3.9+

## Run

```bash
mvn spring-boot:run
```

The API starts at `http://localhost:8080`.

## Endpoints

| # | Method | Endpoint | Purpose |
|---|---|---|---|
| 1 | GET | `/api/stations` | List stations; optional `line` and `q` filters |
| 2 | GET | `/api/stations/{id}` | Get one station |
| 3 | POST | `/api/stations` | Create a station |
| 4 | PUT | `/api/stations/{id}` | Update a station |
| 5 | DELETE | `/api/stations/{id}` | Delete a station |
| 6 | GET | `/api/lines` | List metro lines |
| 7 | GET | `/api/lines/{id}` | Get one line |
| 8 | GET | `/api/stations/{id}/arrivals` | Get upcoming arrivals |
| 9 | GET | `/api/status` | Network and line status |
| 10 | GET | `/api/health` | Basic health response |
| 11 | GET | `/api/stations/{id}/lines` | Lines serving a station |
| 12 | GET | `/api/stations/search?q=...` | Search stations |

## Examples

```bash
curl http://localhost:8080/api/stations
curl "http://localhost:8080/api/stations?line=red"
curl "http://localhost:8080/api/stations/search?q=central"
curl http://localhost:8080/api/stations/1/arrivals
curl http://localhost:8080/api/status
```

Create a station:

```bash
curl -X POST http://localhost:8080/api/stations   -H "Content-Type: application/json"   -d '{
    "name": "Downtown",
    "code": "DWN",
    "lines": ["red", "blue"],
    "latitude": 45.5000,
    "longitude": -73.5600
  }'
```

## Architecture

`MetroController` → `MetroService` → in-memory maps

For a production system, replace the in-memory service with a repository/database layer and add authentication, OpenAPI documentation, pagination, integration tests, and real-time transit data ingestion.
