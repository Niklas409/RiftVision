# RiftVision – Progress

---

## 📌 Gesamt-Roadmap

* Phase 0 – Foundation ✅
* Phase 1 – REST MVP (In-Memory) ✅
* Phase 2 – Database (PostgreSQL + JPA) ✅
* Phase 3 – Clean Architecture & API Standards ✅
* Phase 4 – Security (JWT) ✅
* Phase 5 – Riot API Integration ✅
* Phase 6 – Coach Layer ⏳
* Phase 6.5 – Match Model Refactor ⏳
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
- Riot API Client mit separatem `client/riot` Package
- Riot DTOs sauber getrennt unter `dto/riot/response`
- Riot Account Lookup (`gameName` + `tagLine` → `puuid`)
- Match-ID Lookup via PUUID
- Match-Detail Lookup via Riot Match V5
- Extraktion spielerbezogener Match-Stats
- Eigenes internes DTO `PlayerMatchStatsResponse`
- Recent Stats Endpoint für mehrere Riot-Matches
- Persistenter Riot-Import in PostgreSQL
- Import Response mit `imported` / `skipped`
- Query Parameter `count` für flexiblen Import

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

## Phase 5 – Riot API Integration

* `RiotApiProperties` mit Environment Variable für API Key
* `RiotApiClient` in separatem `client/riot` Package
* Riot Account Endpoint angebunden
* Riot Match IDs Endpoint angebunden
* Riot Match Detail Endpoint angebunden
* DTO-Mapping für Riot Account Response
* DTO-Mapping für Riot Match Response (`metadata`, `info`, `participants`)
* `RiotImportService` als Orchestrierungs-Layer eingeführt
* Interne Riot Endpoints:
  - `/internal/riot/{gameName}/{tagLine}`
  - `/internal/riot/matches/{gameName}/{tagLine}`
  - `/internal/riot/recent-stats/{gameName}/{tagLine}`
  - `/internal/riot/import/{gameName}/{tagLine}?count=...`
* Participant-Findung per `puuid` über Streams
* Eigenes internes Stats-DTO `PlayerMatchStatsResponse`
* `matchId` in `MatchEntity` ergänzt
* `existsByMatchId(...)` im `MatchRepository`
* `getOrCreatePlayer(...)` für Riot-Import
* Import einzelner Matches nur wenn noch nicht vorhanden
* Import Response DTO `ImportMatchesResponse`
* Query Parameter `count` für flexiblen Import
* Matches erfolgreich in PostgreSQL importiert

---

## 🏗 Aktuelle Architektur

* client/riot
* controller
* dto
* dto/riot/response
* exception
* mapper
* domain/entity
* repository
* service
* config
* security

Layer-Struktur:

Controller → Service → Client / Entity → Repository → DB / External API

Security Flow:

Request → JWT Filter → UserDetailsService → SecurityContext → Controller

---

## 🎯 Nächster Fokus

Phase 6 – Coach Layer

Geplante nächste Features:
- Notes pro Match
- Tasks pro Spieler
- Coach ↔ Player Relation

---

## 📊 Projekt-Level

Status: Starkes Junior-Level Backend Fundament mit echter externer API-Integration

System ist:
- Stabil
- Persistierend
- Architektonisch sauber
- API-konsistent
- JWT-gesichert
- Docker-fähig
- Portfolio-tauglich
- Mit echter Import-Pipeline aus einer externen API

---

## Geplanter Refactor

- `MatchEntity` ist aktuell player-zentriert (1 Datensatz = 1 Spieler in 1 Match)
- Duplicate-Check läuft aktuell nur über `matchId`
- Später refactoren auf Eindeutigkeit pro `(player, matchId)` oder vollständiges `Match + MatchParticipant` Modell
- Geplanter Zeitpunkt: nach dem ersten Coach-MVP als eigene Phase 6.5

---

## Legacy-Hinweis

- Der alte manuelle Match-Create-Pfad aus dem ursprünglichen lokalen MVP bleibt aktuell als Legacy-Code bestehen
- Neuer Hauptpfad für echte Match-Daten ist der Riot-Import über `RiotImportService`


---

# 🚧 Phase 6 – Coach Layer (in Progress)

Der Coach Layer erweitert RiftVision um echte Coaching-Funktionalität für Spielerentwicklung.

Ziel:
Spieler können einem Coach zugeordnet werden und Coaches können Matches ihrer Spieler analysieren und kommentieren.

---

## Coach ↔ Student Relation

Neue Domain:
`CoachClientRelationEntity`

Datenbankstruktur:

coach_client_relations

- id
- coach_id
- student_id
- created_at

Features:

- Coach kann Studenten zuweisen
- Studenten eines Coaches abrufen
- Studenten von Coach entfernen
- Duplicate Relations werden verhindert
- Self‑Coaching wird verhindert

Endpoints:

POST `/coach/students/{studentId}`  
→ Student einem Coach zuweisen

GET `/coach/students`  
→ Alle Studenten eines Coaches anzeigen

DELETE `/coach/students/{studentId}`  
→ Student von Coach entfernen

Business Rules:

- Coach muss Rolle `COACH` oder `ADMIN` besitzen
- Student muss Rolle `USER` besitzen
- Coach darf sich nicht selbst zuweisen
- Relation darf nur einmal existieren

---

## Match Notes (Coaching Kontext)

Coaches können Notizen zu Matches ihrer Studenten erstellen.

Wichtig:  
Notes gehören **nicht global zum Match**, sondern zu einem Coaching‑Kontext:

Coach → Student → Match

### Domain Modell

`NoteEntity`

Attribute:

- id
- coach (UserEntity)
- student (UserEntity)
- matchId
- content
- createdAt

Damit können mehrere Coaches oder Spieler dasselbe Match im System haben, ohne dass Notes kollidieren.

---

### Create Note

Endpoint:

POST `/coach/matches/{matchId}/notes`

Request:

{
"studentId": 4,
"content": "Wave zu früh gepusht – Jungle Gank möglich."
}

Response Beispiel:

{
"id": 1,
"matchId": "EUW1_7675782164",
"content": "...",
"coachEmail": "...",
"studentEmail": "...",
"createdAt": "..."
}

Business Logic:

- Coach wird über JWT identifiziert
- Coach Rolle muss `COACH` oder `ADMIN` sein
- Student muss existieren
- Coach ↔ Student Relation muss existieren
- Match muss existieren
- Note wird gespeichert

---

## Nächste geplante Features (Phase 6)

- GET Notes für ein Match
- Update Note
- Delete Note
- Player Tasks / Trainingsaufgaben
- Coach Dashboard Daten
