# RiftVision – Backend Bootcamp Blueprint

---

# 1. Projektvision

RiftVision ist eine Backend-first Analytics & Player Development Plattform
für League of Legends.

Ziel:
Ein produktionsreifes Backend bauen, das sowohl:
- Match-Analyse (Blitz.gg Style)
- als auch Coach-Features

unterstützt.

Dieses Projekt dient als:
- Lernprojekt
- Architekturtraining
- Portfolio-Projekt
- Vorbereitung auf eine Backend-Stelle

---

# 2. Lernziel

Job-ready Backend Developer werden durch:

- REST APIs
- Saubere Layered Architecture
- Business Logic Design
- PostgreSQL & JPA
- Security (JWT + Rollen)
- API Integration
- Production Readiness

---

# 3. Tech Stack

- Java 21
- Spring Boot 4.0.3
- Gradle
- Embedded Tomcat

Später:
- PostgreSQL
- Spring Data JPA
- Spring Security
- JWT
- Docker

---

# 4. Phasenstruktur (0–7)

## Phase 0 – Foundation
Ziel:
- Spring Boot Lifecycle verstehen
- Controller Basics
- Dependency Injection
- Projektstruktur aufbauen

Features:
- GET /health
- Package-Struktur

---

## Phase 1 – REST MVP (In-Memory)

Ziel:
Matches speichern und Stats berechnen.

Domain:
Match
- playerId
- champion
- win
- kills
- deaths
- assists
- playedAt

Endpoints:
- POST /matches
- GET /matches
- GET /players/{playerId}/stats

Lernfokus:
- DTO
- Validation
- Service Layer
- Business Logic
- Streams & Aggregation

---

## Phase 2 – Database Layer

Ziel:
Persistente Daten mit PostgreSQL.

Neue Domain-Elemente:

PlayerEntity
- id
- riotPuuid
- name
- region

MatchEntity
- relation zu Player (ManyToOne)

Lernfokus:
- JPA
- Entities vs DTO
- Repository Pattern
- Pagination
- Sorting

---

## Phase 3 – Clean Architecture & Refactor

Ziel:
Von funktionierend zu sauber strukturiert.

- Mapper Pattern
- Controller dünn halten
- Business Logic nur im Service
- Global Exception Handling
- API Response Standardisierung

---

## Phase 4 – Security Layer

Ziel:
Produktionsreife Authentifizierung.

Neue Domain:

User
- id
- email
- password (hashed)
- role (USER / ADMIN / COACH)

Endpoints:
- POST /auth/register
- POST /auth/login

Lernfokus:
- JWT
- Authentication Flow
- Authorization
- Endpoint Protection

---

## Phase 5 – Riot API Integration

Ziel:
Echte Match-Daten importieren.

Feature:
- POST /import/{playerName}

Lernfokus:
- API Client Design
- Rate Limiting
- Fehlerbehandlung externer APIs
- Caching Thinking

---

## Phase 6 – Coach Layer

Ziel:
Erweiterung um Coaching-Features.

Neue Domain:

Note
- matchId
- content
- createdAt

Task
- playerId
- description
- completed

CoachClientRelation
- coachId
- playerId

---

## Phase 7 – Production Readiness

Ziel:
Industrieniveau erreichen.

- Logging
- Actuator
- Health Monitoring
- Docker
- Docker Compose
- Deployment
- Performance Basics

---

# 5. Arbeitsweise

- Kleine Schritte (15–30 Minuten)
- Keine Komplettlösungen
- Code wird selbst geschrieben
- Review wie Senior Developer
- README_PROGRESS wird nach jeder Session aktualisiert

---

# 6. Architektur-Layout

Geplante Package-Struktur:

riftvision
├── controller
├── service
├── model
├── dto
├── repository
├── config
├── exception