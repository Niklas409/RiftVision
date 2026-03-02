# RiftVision – Progress

---

# Gesamt-Roadmap

Phase 0 – Foundation ✅
Phase 1 – REST MVP ✅
Phase 2 – Database ⏳
Phase 3 – Clean Architecture ⏳
Phase 4 – Security ⏳
Phase 5 – Riot API ⏳
Phase 6 – Coach Layer ⏳
Phase 7 – Production ⏳

---

# Aktuelle Phase

Phase 2 – Database (JPA + PostgreSQL)

---

# Done

## Phase 0 – Foundation

- [x] Clean Package-Struktur erstellt
- [x] Root-Package in lowercase refactored
- [x] Alte Experimente entfernt
- [x] GET /health implementiert
- [x] DTO erstellt (HealthResponse)
- [x] Service Layer implementiert (HealthService)
- [x] Controller mit Constructor Injection
- [x] HTTP Status explizit gesetzt
- [x] JSON Response mit Timestamp
- [x] Gradle clean build verstanden

---

## Phase 1 – REST MVP (In-Memory)

### Domain & DTO
- [x] Match Domain definiert
- [x] MatchRequest DTO mit Validation
- [x] PlayerStatsResponse DTO erstellt

### Endpoints
- [x] POST /matches
- [x] GET /matches
- [x] GET /players/{playerId}/stats
- [x] Controller sauber getrennt (Match / Player)

### Business-Logik
- [x] Matches nach playerId filtern
- [x] Wins zählen
- [x] Losses berechnen
- [x] Kills/Deaths/Assists summieren
- [x] KDA berechnen (Division-by-zero abgesichert)

### Streams gelernt
- [x] stream()
- [x] filter()
- [x] mapToInt()
- [x] sum()
- [x] count()
- [x] Pipeline-Denken verstanden

### Architektur & Qualität
- [x] long vs int Unterschied verstanden
- [x] Double-Division korrekt angewendet
- [x] Validation getestet (400 Bad Request)
- [x] Defensive Copy mit List.copyOf()
- [x] Service kapselt State sauber

---

# Architektur aktuell

controller
- HealthController
- MatchController
- PlayerController

service
- HealthService
- MatchService

dto
- HealthResponse
- MatchRequest
- PlayerStatsResponse

model
- Match

repository
- (kommt in Phase 2)

config
- (leer)

exception
- (kommt später)

---

# Wichtige Learnings

- REST-Flow: Controller → Service → DTO
- Constructor Injection statt Field Injection
- Streams als Daten-Pipeline denken
- Business-Logik gehört in Service, nicht in Controller
- Defensive Programming (List.copyOf)
- Edge Cases absichern (Division by zero)
- API-Struktur bewusst designen

---

# Entscheidungen

- Start ohne DB ✅
- Fokus auf REST Fundamentals ✅
- Business-Logik früh implementiert ✅
- Streams früh gelernt ✅
- Saubere Layered Architecture von Anfang an

---

# Next (Phase 2)

- [ ] PostgreSQL lokal einrichten
- [ ] Match als @Entity umwandeln
- [ ] JpaRepository erstellen
- [ ] In-Memory List entfernen
- [ ] Persistente Speicherung testen