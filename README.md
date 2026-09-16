# API Metro — Spring Boot

Une petite API REST pour le transport en commun avec **12 endpoints**, une validation des données, une gestion des codes HTTP et des données d’exemple stockées en mémoire.

## Prérequis

* Java 17+
```bash
java -v
```
* Maven 3.9+
```bash
mvn -v
```

## Lancer l’application

```bash
git clone https://github.com/KevinEJean/API_Metro.git
cd API_Metro
mvn spring-boot:run
```

L’API démarre à l’adresse `http://localhost:8080`.

## Endpoints

| #  | Méthode | Endpoint                      | Description                                           |
| -- | ------- | ----------------------------- | ----------------------------------------------------- |
| 1  | GET     | `/api/stations`               | Liste les stations ; filtres optionnels `line` et `q` |
| 2  | GET     | `/api/stations/{id}`          | Récupère une station                                  |
| 3  | POST    | `/api/stations`               | Crée une station                                      |
| 4  | PUT     | `/api/stations/{id}`          | Met à jour une station                                |
| 5  | DELETE  | `/api/stations/{id}`          | Supprime une station                                  |
| 6  | GET     | `/api/lines`                  | Liste les lignes de métro                             |
| 7  | GET     | `/api/lines/{id}`             | Récupère une ligne                                    |
| 8  | GET     | `/api/stations/{id}/arrivals` | Récupère les prochaines arrivées                      |
| 9  | GET     | `/api/status`                 | Affiche l’état du réseau et des lignes                |
| 10 | GET     | `/api/health`                 | Vérifie l’état de fonctionnement de l’API             |
| 11 | GET     | `/api/stations/{id}/lines`    | Liste les lignes desservant une station               |
| 12 | GET     | `/api/stations/search?q=...`  | Recherche des stations                                |

## Exemples

```bash
curl http://localhost:8080/api/stations
curl "http://localhost:8080/api/stations?line=red"
curl "http://localhost:8080/api/stations/search?q=central"
curl http://localhost:8080/api/stations/1/arrivals
curl http://localhost:8080/api/status
```

Créer une station :

```bash
curl -X POST http://localhost:8080/api/stations \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Downtown",
    "code": "DWN",
    "lines": ["red", "blue"],
    "latitude": 45.5000,
    "longitude": -73.5600
  }'
```

## Architecture

`MetroController` → `MetroService` → cartes de données en mémoire
