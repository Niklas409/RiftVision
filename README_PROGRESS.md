# RiftVision – Progress

---

## 📌 Gesamt-Roadmap

* Phase 0 – Foundation ✅
* Phase 1 – REST MVP (In-Memory) ✅
* Phase 2 – Database (PostgreSQL + JPA) ✅
* Phase 3 – Clean Architecture & API Standards ✅
* Phase 4 – Security (JWT) ⏳
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

## 🏗 Aktuelle Architektur
* controller
* dto
* exception
* mapper
* model.entity
* repository
* service
* config


Layer-Struktur:

Controller → DTO → Service → Entity → Repository → DB

---

## 🧠 Wichtige Learnings

* Unterschied Entity vs DTO
* Warum Entities nicht direkt exposed werden
* Warum 409 statt 500 bei Duplicate
* Relation Modeling mit JPA
* ManyToOne korrekt verstehen
* Hibernate ddl-auto Verhalten
* Docker Compose für DB
* Streams für Aggregation
* API Konsistenz & Naming Disziplin
* Clean Layered Architecture

---

## 🎯 Nächster Fokus

Phase 4 – Security Layer:

* User Entity
* Passwort-Hashing
* JWT Auth
* Rollen (USER / ADMIN / COACH)
* Endpoint Protection

---

## 📊 Projekt-Level

Status: Solides Junior-Level Backend Fundament

System ist:
- Stabil
- Persistierend
- Architektonisch sauber
- API-konsistent
- Docker-fähig