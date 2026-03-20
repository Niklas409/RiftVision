package ch.niklas409.riftvision.dto.response;

import java.time.Instant;

public class PlayerMatchStatsResponse {

    private String matchId;
    private String champion;
    private int kills;
    private int deaths;
    private int assists;
    private boolean win;
    private int teamId;
    private Instant playedAt;

    public PlayerMatchStatsResponse(String matchId, String champion, int kills, int deaths, int assists, boolean win, int teamId, Instant playedAt) {
        this.matchId = matchId;
        this.champion = champion;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.win = win;
        this.teamId = teamId;
        this.playedAt = playedAt;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getChampion() {
        return champion;
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

    public boolean isWin() {
        return win;
    }

    public int getTeamId() {
        return teamId;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setChampion(String champion) {
        this.champion = champion;
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

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }
}
