# RiftVision

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring
Boot](https://img.shields.io/badge/Spring_Boot-Backend-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Docker](https://img.shields.io/badge/Docker-Container-blue)
![Security](https://img.shields.io/badge/Security-JWT-red)
![Status](https://img.shields.io/badge/Project-Active-success)

Backend-first **Analytics & Coaching Platform for League of Legends**.

RiftVision ist ein langfristiges Backend-Projekt, das reale
Backend-Architektur, Security und externe API‑Integration kombiniert.

Ziel ist es, ein **produktionsnahes Backend-System** zu entwickeln, das
später zur Grundlage einer vollständigen Analytics- und
Coaching-Plattform werden kann.

Dieses Projekt dient gleichzeitig als:

-   Backend Portfolio Projekt
-   Architekturtraining
-   Vorbereitung auf eine Backend Developer Stelle
-   Experimentierfeld für Security und API Design

------------------------------------------------------------------------

# Projekt Motivation

Viele League‑of‑Legends Tools konzentrieren sich ausschließlich auf
Statistiken.

RiftVision verfolgt einen anderen Ansatz:

**Coach‑First Player Development**

Die Plattform soll Coaches ermöglichen:

-   Matchdaten zu analysieren
-   individuelle Coaching‑Notizen zu erstellen
-   Trainingsaufgaben zu definieren
-   Spielerfortschritt zu tracken

Das Backend bildet dafür die technische Grundlage.

------------------------------------------------------------------------

# Key Features

-   REST API für Player, Matches und Stats
-   Riot Games API Integration
-   Match Import Pipeline
-   PostgreSQL Datenpersistenz
-   JWT Authentication
-   Secure Password Hashing (BCrypt)
-   DTO Mapping Pattern
-   Global Exception Handling
-   Dockerisierte lokale Entwicklungsumgebung
-   Konsistente API Response Struktur

------------------------------------------------------------------------

# Backend Skills Demonstrated

Dieses Projekt demonstriert praktische Erfahrung mit:

-   REST API Design
-   Layered Backend Architecture
-   Authentication & Authorization
-   Secure Password Storage
-   External API Integration
-   DTO Separation
-   Exception Handling
-   Database Persistence
-   Clean Code Organisation

------------------------------------------------------------------------

# Tech Stack

## Backend

-   Java 21
-   Spring Boot
-   Spring Security
-   Spring Data JPA
-   JWT Authentication

## Infrastruktur

-   PostgreSQL
-   Docker / Docker Compose
-   Embedded Tomcat

## External APIs

-   Riot Games API

------------------------------------------------------------------------

# Architektur

RiftVision verwendet eine klassische **Layered Architecture**.

    Controller
       ↓
    DTO Layer
       ↓
    Service Layer
       ↓
    Repository Layer
       ↓
    PostgreSQL

External API Flow:

    Controller
       ↓
    Service
       ↓
    Riot Client
       ↓
    Riot API

Security Flow:

    Request
     ↓
    JWT Filter
     ↓
    UserDetailsService
     ↓
    SecurityContext
     ↓
    Controller

------------------------------------------------------------------------

# Riot Match Import Pipeline

RiftVision kann Matchdaten automatisch über die Riot API importieren.

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
    Participant finden
            ↓
    Stats DTO erzeugen
            ↓
    MatchEntity speichern
            ↓
    PostgreSQL

Beispiel Response:

``` json
{
  "message": "Success",
  "data": {
    "imported": 2,
    "skipped": 3
  }
}
```

------------------------------------------------------------------------

# API Endpoints

## Public

    GET /health
    POST /auth/register
    POST /auth/login

## Protected

    POST /players
    POST /matches
    GET /matches
    GET /players/{playerId}/stats

Authorization Header:

    Authorization: Bearer <token>

------------------------------------------------------------------------

# Projektstruktur

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

------------------------------------------------------------------------

# Local Setup

PostgreSQL starten:

    docker compose up -d

Backend starten:

    ./gradlew bootRun

Windows:

    gradlew.bat bootRun

Server läuft auf:

    http://localhost:8080

------------------------------------------------------------------------

# Roadmap

## Completed

Phase 0 -- Foundation\
Phase 1 -- REST MVP\
Phase 2 -- Database\
Phase 3 -- Clean Architecture\
Phase 4 -- Security (JWT)\
Phase 5 -- Riot API Integration\
Phase 6 -- Coach Layer\
Phase 6.5 -- Match Model Refactor

## In Progress

Phase 6.6 -- Match Analytics Endpoint

## Planned

Phase 7 -- Production Readiness

------------------------------------------------------------------------

# Langfristige Vision

RiftVision soll langfristig eine Plattform werden für:

-   Match Analytics
-   Coaching Tools
-   Player Development
-   Trainingssysteme
-   Fortschrittsanalyse

------------------------------------------------------------------------

# Autor

**Niklas Reubold**

Backend Developer mit Fokus auf:

-   Backend Architecture
-   API Development
-   Security
