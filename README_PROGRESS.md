# RiftVision – Progress

---

## 📌 Gesamt-Roadmap

* Phase 0 – Foundation ✅
* Phase 1 – REST MVP (In-Memory) ✅
* Phase 2 – Database (PostgreSQL + JPA) ✅
* Phase 3 – Clean Architecture & API Standards ✅
* Phase 4 – Security (JWT) ✅
* Phase 5 – Riot API Integration ⏳
* Phase 6 – Coach Layer ⏳
* Phase 7 – Production ⏳

---

## 🚀 Aktueller Stand

Backend ist vollständig funktionsfähig mit:

- Persistenter PostgreSQL-Datenbank (Docker)
- JPA Entities & Relation (Player ↔ Match)
- Saubere DTO-Trennung (Request / Response)
- Mapper Pattern (Entity → DTO)
- Global Exception Handling
- 409 Conflict Handling bei Duplicate
- Konsistente API-Naming-Strategie (`playerId`)
- Gerundete KDA-Berechnung
- Einheitliche ApiResponse-Hülle
- Spring Security mit JWT Authentication
- Passwort-Hashing mit BCrypt
- Register / Login Flow
- Geschützte Endpoints mit JWT
- Konsistente 401 Unauthorized Responses

---

# ✅ Abgeschlossen

---

## Phase 0 – Foundation

* Clean Package-Struktur
* GET `/health`
* Constructor Injection
* Gradle Build Verständnis

---

## Phase 1 – REST MVP

* Match Domain
* In-Memory Speicherung
* Stats Aggregation mit Streams
* Defensive Programming
* Validation
* Service-Layer eingeführt

---

## Phase 2 – Database Layer

* PostgreSQL via Docker Compose
* `PlayerEntity`
  - unique `playerId`
* `MatchEntity`
  - ManyToOne Relation zu Player
* JPA Repository Pattern
* Persistente Speicherung
* DB-basierte Stats-Berechnung
* Hibernate Lifecycle verstanden
* DB Reset via Docker Volumes

---

## Phase 3 – Clean Architecture

* Entities werden **nicht** direkt exposed
* Einführung von:
  - `PlayerRequest`
  - `PlayerResponse`
  - `MatchRequest`
  - `MatchResponse`
  - `PlayerStatsResponse`
* `PlayerMapper`
* `MatchMapper`
* Controller nur DTO-basierte Kommunikation
* GlobalExceptionHandler erweitert:
  - 400 Validation
  - 404 ResourceNotFound
  - 409 DataIntegrityViolation
  - 500 Fallback
* Konsistente API-Namensgebung (`playerId`)
* KDA sauber auf 2 Nachkommastellen gerundet

---

## Phase 4 – Security (JWT)

* `UserEntity` eingeführt
* `Role` Enum (`USER`, `ADMIN`, `COACH`)
* `UserRepository`
* Register Endpoint `/auth/register`
* Login Endpoint `/auth/login`
* Passwort-Hashing mit BCrypt
* `JwtService`
* `JwtAuthenticationFilter`
* `CustomUserDetailsService`
* `SecurityConfig` mit geschützten Endpoints
* Stateless Authentication mit JWT
* Custom 401 Unauthorized Response

---

## 🏗 Aktuelle Architektur

* controller
* dto
* exception
* mapper
* model.entity
* repository
* service
* config
* security

Layer-Struktur:

Controller → DTO → Service → Entity → Repository → DB

Security Flow:

Request → JWT Filter → UserDetailsService → SecurityContext → Controller

---

## 🎯 Nächster Fokus

Phase 5 – Riot API Integration

- Riot API Client bauen
- Import Endpoint definieren
- Externe API Fehler behandeln
- Erste echte Match-Daten importieren

---

## 📊 Projekt-Level

Status: Starkes Junior-Level Backend Fundament

System ist:
- Stabil
- Persistierend
- Architektonisch sauber
- API-konsistent
- JWT-gesichert
- Docker-fähig
- Portfolio-tauglich
