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

## ▶️ How to Run (local)

PostgreSQL starten:

docker compose up -d

App starten:

./gradlew bootRun

Windows:

.\gradlew.bat bootRun

Server läuft auf:

http://localhost:8080

---

## 🏗 Projektstruktur

src/main/java/ch/niklas409/riftvision

config  
controller  
domain/entity  
dto/request  
dto/response  
exception  
mapper  
repository  
security  
service

Layer-Struktur:

Controller → DTO → Service → Entity → Repository → DB

Security Flow:

Request → JWT Filter → UserDetailsService → SecurityContext → Controller

---

## 📌 Roadmap

Phase 0 – Foundation ✅  
Phase 1 – REST MVP ✅  
Phase 2 – Database ✅  
Phase 3 – Clean Architecture ✅  
Phase 4 – Security (JWT) ✅  
Phase 5 – Riot API Integration ⏳  
Phase 6 – Coach Layer ⏳  
Phase 7 – Production ⏳

---

## 🎯 Ziel

Ein produktionsnahes Backend-Projekt mit realistischen Architekturentscheidungen und Security,
das Schritt für Schritt Richtung Enterprise-Level erweitert wird.
