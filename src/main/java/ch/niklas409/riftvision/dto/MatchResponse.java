package ch.niklas409.riftvision.dto;

import java.time.Instant;

public class MatchResponse {

    private Long id;
    private String playerId;
    private String champion;
    private boolean win;
    private int kills;
    private int deaths;
    private int assists;
    private Instant playedAt;

    public MatchResponse(Long id, String playerId, String champion, boolean win, int kills, int deaths, int assists, Instant playedAt) {
        this.id = id;
        this.playerId = playerId;
        this.champion = champion;
        this.win = win;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.playedAt = playedAt;
    }

    public Long getId() {
        return id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getChampion() {
        return champion;
    }

    public boolean isWin() {
        return win;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }
}
