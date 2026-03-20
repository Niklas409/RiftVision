# RiftVision

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Docker](https://img.shields.io/badge/Docker-Local_Dev-blue)
![Security](https://img.shields.io/badge/Security-JWT-red)
![Frontend](https://img.shields.io/badge/Frontend-React_%2B_Vite-61dafb)
![Status](https://img.shields.io/badge/Status-Active_Development-success)

Backend-first **Analytics- und Coaching-Plattform für League of Legends**.

RiftVision ist ein langfristiges Projekt, mit dem eine realistische Backend-Architektur, Security, Datenmodellierung, externe API-Integration und schrittweise Produktentwicklung aufgebaut werden.

Das Ziel ist nicht nur eine kleine CRUD-Demo, sondern ein **portfolio-taugliches System**, das sich von einem Lernprojekt zu einer echten Coaching- und Player-Development-Plattform weiterentwickeln kann.

---

## Projektziel

RiftVision verfolgt einen **Coach-First Player Development** Ansatz.

Statt nur rohe Statistiken anzuzeigen, soll die Plattform echte Workflows für Spielerentwicklung unterstützen, zum Beispiel:

- mehrere Riot-Accounts pro User verknüpfen
- Matchdaten über die Riot API importieren und dauerhaft speichern
- persönliche Match-Historie anzeigen
- Match-Details mit Teamansicht darstellen
- Spieler einem Coach zuweisen
- Coaching-Notizen zu konkreten Matches schreiben
- Trainingsaufgaben erstellen und verfolgen
- ein Frontend-MVP für echte Nutzerflüsse bereitstellen

Das Projekt dient gleichzeitig als:

- Backend-Portfolio-Projekt
- Architektur- und API-Design-Training
- Security-Übungsprojekt mit JWT und Rollen
- Vorbereitung auf Backend-Developer-Stellen

---

## Aktueller Stand

### Backend

Das Backend ist bereits in einem starken MVP-Zustand und enthält aktuell:

- JWT Authentication mit Register- und Login-Flow
- Rollen-System mit `USER`, `COACH`, `ADMIN`
- Admin-Endpoint zum kontrollierten Freischalten von Coaches
- Riot-API-Integration für Account-Lookup, Match-IDs, Match-Details und Import
- PostgreSQL-Persistenz mit Spring Data JPA
- globales Match-Modell mit `MatchEntity` und `MatchParticipantEntity`
- Match-Participants mit `playerName` und `teamId` in der Detail-Response
- Coach/Student-Beziehungsmodell
- Match-Notes-System für Coaches
- Task-System für Trainingsaufgaben
- User ↔ Player Account-Linking über `UserPlayerLinkEntity`
- Match-Abfragen scoped auf die verknüpften Accounts des aktuellen Users
- einheitliche `ApiResponse<T>`-Hülle
- Global Exception Handling und angepasste Security-Responses

### Frontend

Ein erstes Frontend-MVP wurde bereits in `riftvision-frontend` mit React + Vite gestartet.

Aktuell vorhanden:

- Register-Formular
- Login-Formular
- JWT-Speicherung im Local Storage
- Logout-Flow
- authentifizierte Match-Liste
- Anzeige verknüpfter Riot-Accounts
- Riot-Import-Flow direkt im Frontend
- automatisches Refresh der Match-Liste nach Import
- Match-Detail-View mit Participants
- Team-Darstellung Blau/Rot im Match-Detail
- Anzeige von `playerName` statt nur kryptischer Player-ID

Damit ist RiftVision nicht mehr nur Backend-only. Es existiert bereits ein echter End-to-End-Flow von Login → Account-Linking → Import → Match-Liste → Match-Details.

---

## Kernfeatures

### Authentication & Security

- `POST /auth/register`
- `POST /auth/login`
- BCrypt Passwort-Hashing
- JWT-Erzeugung und Validierung
- eigenes `AuthenticationEntryPoint`
- eigener `AccessDeniedHandler`
- stateless Security Setup
- CORS für lokales Frontend aktiviert

### Riot Integration

- Riot Account Lookup über `gameName` + `tagLine`
- Lookup der letzten Match-IDs über PUUID
- vollständiger Riot Match Detail Lookup
- Import aktueller Matches in PostgreSQL
- persistente Player-Erstellung beim Import
- vollständiger Import aller 10 Teilnehmer eines Matches
- Speicherung von Team-Informationen (`teamId`) pro Participant

### Match-System

- persönliche Match-Liste des eingeloggten Users
- Match-Details mit allen Participants
- Match-Participants inklusive `playerName`
- Match-Participants inklusive `teamId`
- Team-Darstellung Blau/Rot im Frontend
- Player-Stats-Berechnung über importierte MatchParticipants
- Refactor vom alten player-zentrierten Modell auf ein globales Multiplayer-Match-Modell

### Coaching-System

- Coach ↔ Student Zuordnung
- Create / Read / Update / Delete von Notes zu Student-Matches
- Create / Update / Delete von Tasks für Students
- Students können Tasks als erledigt / unerledigt markieren

### Riot Account Linking

- Riot-Accounts dem aktuell eingeloggten User zuordnen
- verknüpfte Riot-Accounts abrufen
- verknüpfte Riot-Accounts wieder entfernen
- mehrere verknüpfte Accounts pro User möglich
- mehrere User können denselben Riot-Account über eine Link-Tabelle referenzieren

---

## Aktuelles Domain-Modell

### Gameplay-Modell

```text
Player
  ↓
MatchParticipant
  ↓
Match
```

### User-Account-Linking

```text
User
  ↓
UserPlayerLink
  ↓
Player
```

### Coaching-Modell

```text
Coach (User)
  ↓
CoachClientRelation
  ↓
Student (User)
```

Diese Trennung ist wichtig, weil:

- `Player` eine Riot-Spielidentität repräsentiert
- `User` einen authentifizierten RiftVision-Account repräsentiert
- `UserPlayerLink` flexible Verknüpfungen erlaubt, ohne einen Riot-Account exklusiv genau einem Plattform-User zuzuordnen

---

## Architektur

RiftVision verwendet eine klassische Layered Architecture.

```text
Controller
   ↓
DTO Layer
   ↓
Service Layer
   ↓
Repository Layer
   ↓
PostgreSQL
```

### Riot API Flow

```text
Controller
   ↓
Service
   ↓
RiotApiClient
   ↓
Riot API
```

### Security Flow

```text
Request
  ↓
JWT Filter
  ↓
UserDetailsService
  ↓
SecurityContext
  ↓
Controller
```

---

## Tech Stack

### Backend

- Java 21
- Spring Boot 4.0.3
- Spring Web
- Spring Validation
- Spring Data JPA
- Spring Security
- JWT (`jjwt`)

### Datenbank & Infrastruktur

- PostgreSQL
- Docker / Docker Compose
- Embedded Tomcat

### Frontend

- React
- Vite
- TypeScript

### Externe API

- Riot Games API

---

## Wichtige Endpoints

### Public

```text
GET  /health
POST /auth/register
POST /auth/login
```

### Protected

```text
POST   /players
GET    /players
DELETE /players/{playerId}

GET    /matches
GET    /matches/{matchId}
GET    /players/{playerId}/stats

POST   /coach/students/{studentId}
GET    /coach/students
DELETE /coach/students/{studentId}

POST   /coach/matches/{matchId}/notes
GET    /coach/matches/{matchId}/notes
PUT    /coach/notes/{noteId}
DELETE /coach/notes/{noteId}

POST   /coach/students/{studentId}/tasks
GET    /coach/students/{studentId}/tasks
PUT    /coach/tasks/{taskId}
DELETE /coach/tasks/{taskId}
PATCH  /tasks/{taskId}/complete
PATCH  /tasks/{taskId}/uncomplete
```

Authorization Header:

```text
Authorization: Bearer <token>
```

---

## Projektstruktur

```text
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
```

Zusätzlich:

```text
riftvision-frontend/
```

für das React/Vite-Frontend.

---

## Lokales Setup

### PostgreSQL starten

```bash
docker compose up -d
```

### Datenbank komplett resetten

```bash
docker compose down -v
docker compose up -d
```

### Backend starten

Linux / macOS:

```bash
./gradlew bootRun
```

Windows:

```bash
gradlew.bat bootRun
```

### Frontend starten

```bash
cd riftvision-frontend
npm install
npm run dev
```

Backend läuft auf:

```text
http://localhost:8080
```

Frontend läuft auf:

```text
http://localhost:5173
```

---

## Roadmap

### Abgeschlossen

- Phase 0 – Foundation
- Phase 1 – REST MVP
- Phase 2 – Database
- Phase 3 – Clean Architecture
- Phase 4 – Security (JWT)
- Phase 5 – Riot API Integration
- Phase 6 – Coach Layer
- Phase 6.5 – Match Model Refactor
- Phase 6.6 – Match Details Endpoint
- Phase 6.7 – Role Management (Admin)
- Phase 6.8 – Riot Account Linking System
- Phase 6.9 – Frontend Match Flow & Team Rendering

### In Arbeit

- Frontend MVP
- UX-Verbesserungen für Match- und Team-Darstellung
- weiterer End-to-End-Flow zwischen Backend und Frontend

### Später geplant

- Production Readiness
- Deployment
- härtere Import- und Ownership-Regeln
- erweitertes Coaching-Frontend
- weitere Analytics- und Review-Features

---

## Langfristige Vision

RiftVision soll langfristig eine Plattform werden für:

- Match Analytics
- Coaching Tools
- Player Development
- Trainingssysteme
- Fortschrittsanalyse
- mehrere Nutzerrollen und gemeinsame Account-Nutzung

---

## Autor

**Niklas Reubold**

Fokus:

- Backend Architecture
- API Development
- Security
- produktionsnahes Denken über reale Systeme
