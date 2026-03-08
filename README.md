# RiftVision

Coach-first Analytics & Player Development Backend für League of Legends.

RiftVision ist ein langfristiges Backend-Projekt zum systematischen Aufbau von Backend-Kompetenz:

REST → Business Logic → Validation → Datenbank → Clean Architecture → Security → Production Readiness.

Das Projekt dient gleichzeitig als:

- Lernprojekt
- Architekturtraining
- Portfolio-Projekt
- Vorbereitung auf eine Backend-Stelle

---

# 🚀 Aktueller Stand

RiftVision ist aktuell ein funktionierendes Spring Boot Backend mit:

- REST API für Player, Matches und Stats
- PostgreSQL Persistenz via Docker
- JPA Entities + Repository Layer
- sauberer DTO-Trennung (Request / Response)
- Mapper Pattern
- Global Exception Handling
- konsistenter ApiResponse-Hülle
- Spring Security mit JWT Authentication
- Passwort-Hashing mit BCrypt
- geschützten Endpoints via JWT Filter
- konsistenten 401 Unauthorized Responses

---

# 🛠 Tech Stack

- Java 21
- Spring Boot 4.0.3
- Gradle
- PostgreSQL
- Spring Data JPA
- Spring Security
- JWT
- Docker / Docker Compose
- Embedded Tomcat

---

# 📦 Features

## Core Backend

- Health Check Endpoint
- Player anlegen
- Matches speichern
- Matches abrufen
- Player Stats berechnen

## Architektur

- Layered Architecture
- DTO Request / Response Trennung
- Mapper Pattern
- Global Exception Handling
- ApiResponse Standardisierung

## Datenbank

- PostgreSQL Persistenz
- JPA Entity Mapping
- ManyToOne Relation zwischen Match und Player

## Security

- User Registration
- User Login
- Passwort-Hashing mit BCrypt
- JWT Token Generierung
- JWT Token Validierung
- geschützte Endpoints
- Custom 401 Unauthorized JSON Response

---

# 🌐 API Endpoints

## Public Endpoints

GET /health

POST /auth/register
POST /auth/login

## Protected Endpoints

POST /players
POST /matches
GET /matches
GET /players/{playerId}/stats

Authorization Header:

Authorization: Bearer <token>

---

# ▶️ How to Run (local)

Docker starten:

docker compose up -d

App starten:

./gradlew bootRun

Windows:

.\gradlew.bat bootRun

Server läuft auf:

http://localhost:8080
