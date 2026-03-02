# RiftVision

Coach-first Analytics & Player Development Backend für League of Legends.

RiftVision ist ein Backend-Projekt zum systematischen Aufbau von Backend-Kompetenz:
REST → Business Logic → Validation → Datenbank → Security → Production.

---

## 🚀 Aktueller Stand

- REST API mit Spring Boot
- In-Memory Match Speicherung
- Player Stats Berechnung (Wins, Losses, KDA)
- Input Validation (@NotBlank, @NotNull, @Min)
- Saubere Layered Architecture (Controller → Service → DTO → Model)
- Stream-basierte Aggregation (filter, mapToInt, sum, count)

Nächster Schritt:
- PostgreSQL + JPA Integration
- Persistente Speicherung
- Global Exception Handling
- Security Layer (JWT)

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 4.0.3
- Gradle
- In-Memory Storage (aktuell)
- (coming) PostgreSQL + JPA
- (coming) Spring Security (JWT)

---

## 📦 Features (aktueller Stand)

- Health Check Endpoint
- Matches speichern & abrufen
- Player Stats berechnen:
    - Matches
    - Wins
    - Losses
    - Kills
    - Deaths
    - Assists
    - KDA
- Input Validation mit automatischem 400 Bad Request

---

## 🌐 API Endpoints

### Health

GET `/health`

Beispiel-Response:

```json
{
"status": "UP",
"service": "RiftVision",
"timestamp": "2026-03-01T20:49:47.2967509"
}
```
---

### Matches

POST `/matches`

Beispiel-Request:

```json
{
"playerId": "niklas409",
"champion": "Ahri",
"win": true,
"kills": 10,
"deaths": 2,
"assists": 6,
"playedAt": "2026-03-01T21:30:00"
}
```

GET `/matches`

Gibt alle gespeicherten Matches zurück.

---

### Player Stats

GET `/players/{playerId}/stats`

Beispiel:
GET `/players/niklas409/stats`

Beispiel-Response:

```json
{
"playerId": "niklas409",
"matches": 1,
"wins": 1,
"losses": 0,
"kills": 10,
"deaths": 2,
"assists": 6,
"kda": 8.0
}
```

KDA Formel:

kda = (kills + assists) / max(1, deaths)

Division-by-zero wird abgesichert.

---

## ▶️ How to Run (local)

1. Repository klonen
2. Projekt öffnen (z. B. IntelliJ)
3. `RiftVisionApplication` starten

Oder via CLI:

./gradlew bootRun

Server läuft standardmäßig auf:

http://localhost:8080

---

## 🧪 Testing

Empfohlen:

- IntelliJ HTTP Client (.http Datei)
- Postman
- curl

---

## 🏗 Projektstruktur

controller/
- HealthController
- MatchController
- PlayerController

service/
- HealthService
- MatchService

dto/
- HealthResponse
- MatchRequest
- PlayerStatsResponse

model/
- Match

---

## 🧠 Wichtige Learnings

- REST Architektur mit klarer Layer-Trennung
- Constructor Injection
- Business Logic gehört in den Service
- Stream Pipeline:
    - filter()
    - mapToInt()
    - sum()
    - count()
- long vs int Unterschied
- Double-Division korrekt anwenden
- Defensive Programming (List.copyOf)
- Validation mit @Valid

---

## 📌 Roadmap

Phase 0 – Foundation ✅  
Phase 1 – REST MVP (In-Memory) ✅  
Phase 2 – Database (PostgreSQL + JPA) ⏳  
Phase 3 – Clean Architecture ⏳  
Phase 4 – Security ⏳  
Phase 5 – Riot API ⏳  
Phase 6 – Coach Layer ⏳  
Phase 7 – Production ⏳

---

## 🎯 Ziel

Ein produktionsnahes, sauberes Backend-Projekt,
das reale Architektur-Entscheidungen abbildet
und schrittweise Richtung Enterprise-Level ausgebaut wird.