package ch.niklas409.riftvision.model;

import java.time.LocalDateTime;

public class Match {

    private String playerId;
    private String champion;
    private boolean win;
    private int kills;
    private int deaths;
    private int assists;
    private LocalDateTime playedAt;

    public Match(String playerId, String champion, boolean win, int kills, int deaths, int assists, LocalDateTime playedAt) {
        this.playerId = playerId;
        this.champion = champion;
        this.win = win;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.playedAt = playedAt;
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

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
}
