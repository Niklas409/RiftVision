# RiftVision

Coach-first Analytics & Player Development Backend für League of Legends.

RiftVision ist ein langfristiges Backend-Projekt zum systematischen Aufbau von Backend-Kompetenz:

REST → Business Logic → Validation → Datenbank → Clean Architecture → Security → API Integration → Production Readiness.

Das Projekt dient gleichzeitig als:
- Lernprojekt
- Architekturtraining
- Portfolio-Projekt
- Vorbereitung auf eine Backend-Stelle

---

## 🚀 Aktueller Stand

RiftVision ist aktuell ein funktionierendes Spring Boot Backend mit:

- REST API für Player, Matches und Stats
- PostgreSQL Persistenz via Docker
- JPA Entities + Repository Layer
- Saubere DTO-Trennung (Request / Response)
- Mapper Pattern
- Global Exception Handling
- Konsistente ApiResponse-Hülle
- Spring Security mit JWT Authentication
- Passwort-Hashing mit BCrypt
- Geschützte Endpoints via JWT Filter
- Konsistente 401 Unauthorized Responses
- Riot API Integration über separaten Client-Layer
- Riot Account Lookup (`gameName` + `tagLine` → `puuid`)
- Match-ID Lookup und Match-Detail Lookup über Riot Match V5
- Eigenes internes Stats-DTO für letzte Match-Stats
- Import-Pipeline von Riot → DTO → Entity → PostgreSQL
- Import Response mit `imported` / `skipped`

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 4
- Gradle
- PostgreSQL
- Spring Data JPA
- Spring Security
- JWT
- Docker / Docker Compose
- Embedded Tomcat
- Riot Games API

---

## 🌐 API Endpoints

### Public Endpoints

GET /health

POST /auth/register  
POST /auth/login

### Protected Endpoints

POST /players  
POST /matches  
GET /matches  
GET /players/{playerId}/stats

Authorization Header:

Authorization: Bearer <token>

### Internal Riot Endpoints

GET /internal/riot/{gameName}/{tagLine}  
GET /internal/riot/matches/{gameName}/{tagLine}  
GET /internal/riot/recent-stats/{gameName}/{tagLine}  
POST /internal/riot/import/{gameName}/{tagLine}?count=5

---

## 🔐 Authentication Flow

### Register

```json
{
  "email": "niklas@test.com",
  "password": "123456"
}
```

Passwort wird mit BCrypt gehasht und der User gespeichert.

### Login

```json
{
  "email": "niklas@test.com",
  "password": "123456"
}
```

Response:

```json
{
  "message": "Success",
  "data": {
    "message": "Login successful",
    "token": "JWT_TOKEN"
  }
}
```

---

## 🌍 Riot Import Flow

```text
Riot ID + TagLine
        ↓
Account API
        ↓
PUUID
        ↓
Match IDs
        ↓
Match Details
        ↓
Participant per PUUID finden
        ↓
Eigenes Stats-DTO bauen
        ↓
MatchEntity speichern
        ↓
PostgreSQL
```

Import Response Beispiel:

```json
{
  "message": "Success",
  "data": {
    "imported": 2,
    "skipped": 3
  }
}
```

---

## ▶️ How to Run (local)

PostgreSQL starten:

```bash
docker compose up -d
```

App starten:

```bash
./gradlew bootRun
```

Windows:

```bash
.\gradlew.bat bootRun
```

Server läuft auf:

http://localhost:8080

---

## 🏗 Projektstruktur

src/main/java/ch/niklas409/riftvision

config  
controller  
client/riot  
domain/entity  
dto/request  
dto/response  
dto/riot/response  
exception  
mapper  
repository  
security  
service

Layer-Struktur:

Controller → DTO / API Request → Service → Client / Entity → Repository → DB / External API

Security Flow:

Request → JWT Filter → UserDetailsService → SecurityContext → Controller

---

## 📌 Roadmap

Phase 0 – Foundation ✅  
Phase 1 – REST MVP ✅  
Phase 2 – Database ✅  
Phase 3 – Clean Architecture ✅  
Phase 4 – Security (JWT) ✅  
Phase 5 – Riot API Integration ✅  
Phase 6 – Coach Layer ⏳  
Phase 6.5 – Match Model Refactor ⏳  
Phase 7 – Production ⏳

---

## 🎯 Ziel

Ein produktionsnahes Backend-Projekt mit realistischen Architekturentscheidungen, Security und echter externer API-Integration,
das Schritt für Schritt Richtung Enterprise-Level erweitert wird.
