# RiftVision – Progress

---

## 📌 Gesamt-Roadmap

* Phase 0 – Foundation ✅
* Phase 1 – REST MVP (In-Memory) ✅
* Phase 2 – Database (PostgreSQL + JPA) ⏳
* Phase 3 – Clean Architecture & API Standards ✅
* Phase 4 – Security (JWT) ⏳
* Phase 5 – Riot API ⏳
* Phase 6 – Coach Layer ⏳
* Phase 7 – Production ⏳

---

## 🚀 Aktuelle Phase

**Phase 2 – Database Integration (PostgreSQL + JPA)**

---

# ✅ Abgeschlossen

---

## Phase 0 – Foundation

* Clean Package-Struktur erstellt
* Root-Package lowercase refactored
* GET `/health` implementiert
* HealthResponse DTO erstellt
* HealthService implementiert
* Constructor Injection verwendet
* Gradle clean build als Fix bei Build-Problemen verstanden

---

## Phase 1 – REST MVP (In-Memory)

### Domain & DTO

* Match Domain definiert
* MatchRequest DTO mit Validation erstellt
* PlayerStatsResponse DTO erstellt

### Endpoints

* POST `/matches`
* GET `/matches`
* GET `/players/{playerId}/stats`
* Controller sauber getrennt (Match / Player)

### Business Logic

* Matches nach playerId gefiltert
* Wins gezählt
* Losses berechnet (`matches - wins`)
* Kills / Deaths / Assists summiert
* KDA korrekt berechnet
* Division-by-zero abgesichert

### Streams gelernt

* `stream()`
* `filter()`
* `mapToInt()`
* `sum()`
* `count()`
* Pipeline-Denken verstanden

### Qualität & Defensive Programming

* Unterschied zwischen `long` und `int` verstanden
* Double-Division korrekt angewendet
* Validation getestet (400 Bad Request)
* Defensive Copy mit `List.copyOf()` implementiert
* Service kapselt internen State sauber

---

## Phase 3 – Clean Architecture & API Standards

### Architektur-Verbesserung

* Mapper Pattern eingeführt
* Controller dünn gehalten
* Business Logic ausschließlich im Service

### Global Exception Handling

* `FieldErrorResponse` DTO erstellt
* `ApiErrorResponse` DTO erstellt
* `ResourceNotFoundException` implementiert
* `GlobalExceptionHandler` implementiert

Abgedeckte Fälle:

* 400 – Validation Errors
* 404 – Resource Not Found
* 500 – Generic Exception Fallback

Error Response enthält:

* status
* message
* errors[]
* path

### API Response Standardisierung

* Generisches `ApiResponse<T>` eingeführt
* Einheitliche Success Response Struktur
* Alle GET & POST Endpoints angepasst

---

## 🏗 Aktuelle Architektur

### controller

* HealthController
* MatchController
* PlayerController

### service

* HealthService
* MatchService

### mapper

* MatchMapper

### dto

* HealthResponse
* MatchRequest
* PlayerStatsResponse
* ApiResponse

### model

* Match

### exception

* ApiErrorResponse
* FieldErrorResponse
* ResourceNotFoundException
* GlobalExceptionHandler

### repository

* (kommt in Phase 2)

### config

* (noch leer)

---

## 🧠 Wichtige Learnings

* REST-Flow: Controller → Mapper → Service → DTO
* Constructor Injection statt Field Injection
* Streams als Daten-Pipeline denken
* Business-Logik gehört in den Service, nicht in den Controller
* Defensive Programming (`List.copyOf`, Edge Cases absichern)
* Generics verstehen (`ApiResponse<T>`)
* Einheitliche API-Struktur designen
* Professionelles Global Exception Handling implementieren

---

## 🎯 Entscheidungen

* Start ohne DB ✅
* Fokus auf REST Fundamentals ✅
* Business-Logik früh implementiert ✅
* Streams früh gelernt ✅
* Saubere Layered Architecture von Anfang an ✅

---

## ⏭ Next – Phase 2 (Database)

* [ ] PostgreSQL via Docker Compose starten
* [ ] application.yml konfigurieren
* [ ] Match als JPA Entity modellieren
* [ ] JpaRepository erstellen
* [ ] In-Memory List entfernen
* [ ] Persistente Speicherung testen
* [ ] Stats weiterhin korrekt aus DB berechnen