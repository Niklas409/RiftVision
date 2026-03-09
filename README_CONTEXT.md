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
- PostgreSQL
- Spring Data JPA
- Spring Security
- JWT
- Docker
- Riot Games API

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

Features:
- Riot API Key via Environment Variable
- Riot Account Lookup (`gameName` + `tagLine`)
- Match-ID Lookup via PUUID
- Match-Detail Lookup
- Extraktion spielerbezogener Match-Stats
- Internal Riot Endpoints für Lookup und Recent Stats
- Persistenter Import in PostgreSQL
- Import Response mit `imported` / `skipped`

Lernfokus:
- API Client Design
- DTO Mapping externer APIs
- Service-Orchestrierung
- Externe API → internes DTO → Entity Mapping
- Duplicate Handling
- Persistenter Import

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

## Phase 6.5 – Match Model Refactor

Ziel:
Vom aktuellen player-zentrierten MVP-Modell auf ein fachlich sauberes Match-Modell wechseln.

Geplantes Zielmodell:
- `MatchEntity` als globales Match
- `MatchParticipantEntity` als Join-/Teilnahme-Entity
- `matchId` global unique auf Match-Ebene
- Eindeutigkeit pro `(match, player)` auf Participant-Ebene

Warum später?
- aktueller MVP-Fokus liegt auf funktionierendem Riot-Import und Coach-MVP
- vollständiger Refactor lohnt sich nach Phase 6 deutlich mehr

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

Aktuelle Package-Struktur:

```text
riftvision
├── client/riot
├── config
├── controller
├── domain/entity
├── dto/request
├── dto/response
├── dto/riot/response
├── exception
├── mapper
├── repository
├── security
└── service
```

Aktuelle Layer-Struktur:

Controller → Service → Client / Entity → Repository → DB / External API
