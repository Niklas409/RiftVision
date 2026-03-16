# RiftVision – Progress

---

## 📌 Gesamt-Roadmap

- Phase 0 – Foundation ✅
- Phase 1 – REST MVP (In-Memory) ✅
- Phase 2 – Database (PostgreSQL + JPA) ✅
- Phase 3 – Clean Architecture & API Standards ✅
- Phase 4 – Security (JWT) ✅
- Phase 5 – Riot API Integration ✅
- Phase 6 – Coach Layer ✅
- Phase 6.5 – Match Model Refactor ✅
- Phase 6.6 – Match Details Endpoint ✅
- Phase 6.7 – Role Management (Admin) ✅
- Phase 6.8 – Riot Account Linking System ✅
- Phase 7 – Production / Hardening ⏳
- Frontend MVP – gestartet ⏳

---

## 🚀 Aktueller Projektstand

RiftVision ist inzwischen deutlich mehr als nur eine Backend-Übungs-App.

Aktuell vorhanden:

### Backend

- funktionierende JWT Authentication
- Rollen-System (`USER`, `COACH`, `ADMIN`)
- Riot API Integration mit persistentem Import
- globales Multiplayer-Match-Modell
- Coach/Student-Beziehungsmodell
- Notes- und Task-System für Coaching-Workflows
- Account-Linking-System für mehrere Riot-Accounts pro User
- persönliche Match-Liste scoped auf verknüpfte Riot-Accounts
- konsistente API-Response-Hülle und Exception Handling

### Frontend

- React + Vite Projekt im Monorepo ergänzt
- Register-View
- Login-View
- JWT-Speicherung im Local Storage
- authentifizierte Match-Liste

---

## ✅ Abgeschlossene Arbeit nach Phasen

---

## Phase 0 – Foundation

Abgeschlossen:

- Package-Struktur erstellt
- `GET /health`
- Constructor Injection eingeführt
- Gradle-Projektsetup verstanden

---

## Phase 1 – REST MVP (In-Memory)

Abgeschlossen:

- frühe Match-Domain
- In-Memory Speicherung
- Stats-Aggregation
- Validation-Basics
- erste Service-Layer-Struktur

---

## Phase 2 – Database Layer

Abgeschlossen:

- PostgreSQL via Docker Compose
- JPA Repositories eingeführt
- persistente Player-Speicherung
- persistente Match-Speicherung
- datenbankbasierte Stats-Berechnung
- Hibernate Lifecycle besser verstanden
- lokaler DB-Reset-Workflow geübt

---

## Phase 3 – Clean Architecture & API Standards

Abgeschlossen:

- Entities werden nicht mehr direkt exposed
- DTO-basierte Controller-Kommunikation
- Mapper-Layer eingeführt
- `ApiResponse<T>` Wrapper eingeführt
- Global Exception Handling verbessert
- konsistente API-Namensgebung mit `playerId`
- KDA-Rundung verbessert

Eingeführte / genutzte DTOs:

- `PlayerRequest`
- `PlayerResponse`
- `MatchRequest`
- `MatchResponse`
- `PlayerStatsResponse`

---

## Phase 4 – Security (JWT)

Abgeschlossen:

- `UserEntity` eingeführt
- `Role` Enum mit `USER`, `COACH`, `ADMIN`
- `UserRepository`
- Register Endpoint
- Login Endpoint
- BCrypt Passwort-Hashing
- JWT Service
- JWT Authentication Filter
- Custom User Details Service
- stateless Security Config
- Custom 401 Response
- Custom AccessDenied Handling
- CORS für lokales Frontend

---

## Phase 5 – Riot API Integration

Abgeschlossen:

- `RiotApiClient` eingeführt
- Riot API Properties / Config ergänzt
- Riot Account Lookup Endpoint
- Riot Match-ID Lookup Endpoint
- Riot Match-Detail Lookup Endpoint
- Riot DTO Mapping unter `dto/riot/response`
- `RiotImportService` als Orchestrierungs-Layer eingeführt
- internes Stats-DTO `PlayerMatchStatsResponse`
- persistenter Riot-Import in PostgreSQL
- Import-Response mit `imported` / `skipped`
- Query Parameter `count` für flexible Import-Größe

Implementierte interne Endpoints:

- `GET /internal/riot/{gameName}/{tagLine}`
- `GET /internal/riot/matches/{gameName}/{tagLine}`
- `GET /internal/riot/recent-stats/{gameName}/{tagLine}`
- `POST /internal/riot/import/{gameName}/{tagLine}?count=...`

---

## Phase 6 – Coach Layer

Abgeschlossen:

### Coach ↔ Student Relation

Domain:

- `CoachClientRelationEntity`

Features:

- Student einem Coach zuweisen
- Students eines Coaches abrufen
- Students von Coach entfernen
- Duplicate Relations verhindern
- Self-Coaching verhindern

Endpoints:

- `POST /coach/students/{studentId}`
- `GET /coach/students`
- `DELETE /coach/students/{studentId}`

### Match Notes

Domain:

- `NoteEntity`

Features:

- Note erstellen
- Notes für Match + Student abrufen
- Note aktualisieren
- Note löschen
- Ownership-Checks für Coach-Zugriff

Endpoints:

- `POST /coach/matches/{matchId}/notes`
- `GET /coach/matches/{matchId}/notes`
- `PUT /coach/notes/{noteId}`
- `DELETE /coach/notes/{noteId}`

### Player Tasks

Domain:

- `TaskEntity`

Features:

Coach:

- Task erstellen
- Tasks eines Students abrufen
- Task aktualisieren
- Task löschen

Student:

- Task auf erledigt setzen
- Task wieder auf unerledigt setzen

Endpoints:

- `POST /coach/students/{studentId}/tasks`
- `GET /coach/students/{studentId}/tasks`
- `PUT /coach/tasks/{taskId}`
- `DELETE /coach/tasks/{taskId}`
- `PATCH /tasks/{taskId}/complete`
- `PATCH /tasks/{taskId}/uncomplete`

---

## Phase 6.5 – Match Model Refactor

Abgeschlossen:

Das alte player-zentrierte Match-Modell wurde durch ein saubereres Multiplayer-Modell ersetzt.

Neue Domain:

### `MatchEntity`

- globales Riot Match
- eindeutige `matchId`
- `playedAt`

### `MatchParticipantEntity`

- referenziert `MatchEntity`
- referenziert `PlayerEntity`
- champion
- kills
- deaths
- assists
- win

Abgeschlossene Arbeit:

- Einführung von `MatchParticipantEntity`
- `MatchEntity` auf globales Match-Modell reduziert
- Speicherung aller 10 Spieler eines Riot-Matches
- Riot-Import speichert vollständige Match-Participants
- automatische Player-Erstellung über `getOrCreatePlayer(...)`
- Speicherung von RiotId (`gameName` + `tagLine`) beim Player-Import
- Stats-Berechnung refactored auf Basis von `MatchParticipantEntity`
- `MatchRepository` von alter Player-Query bereinigt
- `MatchResponse` / `MatchMapper` auf neues Match-Modell angepasst

---

## Phase 6.6 – Match Details Endpoint

Abgeschlossen:

Ziel:
Ein komplettes Riot Match mit allen 10 Participants abrufen können. Das bildet die Grundlage für spätere Coaching-Analysen und Frontend-Match-Views.

Endpoint:

- `GET /matches/{matchId}`

Response enthält:

- `matchId`
- `playedAt`
- `participants` (alle 10 Spieler)

Neue Response-Modelle:

- `MatchDetailsResponse`
- `MatchParticipantResponse`

Umsetzung:

- neuer Service-Endpoint `getMatchByMatchId(...)`
- Mapping von `MatchParticipantEntity` → `MatchParticipantResponse`
- Lazy-Loading Problem mit `@Transactional` gelöst
- Controller Endpoint `GET /matches/{matchId}` ergänzt
- HTTP-Testsuite erweitert

Ergebnis:

Match-Details API liefert vollständige Riot-Match-Daten inklusive aller Participants.

---

## Phase 6.7 – Role Management (Admin)

Abgeschlossen:

Ziel:
Coaches sollen nicht automatisch entstehen, sondern bewusst freigeschaltet werden.

Admin Endpoint:

- `POST /internal/admin/users/{userId}/make-coach`

Features:

- Admin kann registrierten User zum Coach machen
- doppelte Coach-Zuweisung wird verhindert
- Endpoint durch Security geschützt

Security:

- `@PreAuthorize("hasRole('ADMIN')")`

Tests:

- USER → 403
- COACH → 403
- ADMIN → erlaubt

Ergebnis:

Rollenverwaltung für Coaches ist kontrolliert möglich.

---

## Phase 6.8 – Riot Account Linking System

Abgeschlossen:

Ziel:
Riot-Accounts flexibel mit Plattform-Usern verknüpfen, ohne einen Riot-Account exklusiv genau einem User zuzuordnen.

Neue Domain:

### `UserPlayerLinkEntity`

Verknüpft:

- `UserEntity`
- `PlayerEntity`
- `linkedAt`

Neue Struktur:

```text
User
  ↓
UserPlayerLink
  ↓
Player
```

Ergebnis des Refactors:

- alte direkte `Player -> User` Beziehung entfernt
- neue Many-to-Many-artige Verknüpfung über eigene Link-Entity
- mehrere Riot-Accounts pro User möglich
- mehrere User können denselben Riot-Account referenzieren
- Faker-/Claim-Problem deutlich entschärft
- flexiblere Basis für spätere Coach-/Team-Usecases

Neue / angepasste Features:

- Riot-Account zum aktuellen User verknüpfen
- verknüpfte Accounts des aktuellen Users abrufen
- Account-Link des aktuellen Users löschen
- Match-Abfragen auf verknüpfte Accounts umgebaut

Neue / angepasste Endpoints:

- `POST /players`
- `GET /players`
- `DELETE /players/{playerId}`

Technische Änderungen:

- `UserPlayerLinkEntity` eingeführt
- `UserPlayerLinkRepository` eingeführt
- `PlayerService` auf Link-Modell umgebaut
- `MatchService` lädt Player jetzt über `UserPlayerLink`
- alter direkter `Player.user` Zugriff entfernt
- alter `findAllByUser(...)` Zugriff im `PlayerRepository` entfernt

---

## Frontend MVP – gestartet

Aktueller Stand:

- React + Vite Projekt angelegt
- Login-Formular
- Register-Formular
- JWT im Local Storage
- Logout-Flow
- Match-Liste für eingeloggten User

Ziel des nächsten Frontend-Schritts:

- verknüpfte Accounts anzeigen
- Import-Flow im Frontend nutzbar machen
- Match-Details-View bauen
- später Notes und Tasks einbinden

---

## 🏗 Aktuelle Architektur

Packages:

- `client/riot`
- `controller`
- `dto`
- `dto/riot/response`
- `exception`
- `mapper`
- `domain/entity`
- `repository`
- `service`
- `config`
- `security`

Layer-Struktur:

```text
Controller → Service → Client / Entity → Repository → DB / External API
```

Security Flow:

```text
Request → JWT Filter → UserDetailsService → SecurityContext → Controller
```

---

## 🎯 Nächster Fokus

Sinnvolle nächste Schritte:

1. Frontend für verknüpfte Accounts
2. Import-Flow im Frontend
3. Match-Detail-View
4. Match-Import weiter härten / besser auf verknüpfte Accounts abstimmen
5. später Production Readiness

---

## 📊 Projekt-Level

RiftVision zeigt inzwischen praktische Arbeit mit:

- echter externer API-Integration
- Security Layer (JWT)
- relationaler Datenmodellierung
- Coaching-Domain-Modell
- Account-Linking-Architektur
- sauberen REST APIs
- Docker-Datenbank-Setup
- React-Frontend-Basis
