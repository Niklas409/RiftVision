package ch.niklas409.riftvision.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MatchRequest {

    @NotBlank
    private String playerId;
    @NotBlank
    private String champion;
    @NotNull
    private Boolean win;
    @NotNull
    @Min(0)
    private Integer kills;
    @NotNull
    @Min(0)
    private Integer deaths;
    @NotNull
    @Min(0)
    private Integer assists;
    @NotNull
    private Instant playedAt;

    public MatchRequest(String playerId, String champion, Boolean win, Integer kills, Integer deaths, Integer assists, Instant playedAt) {
        this.playerId = playerId;
        this.champion = champion;
        this.win = win;
        this. kills = kills;
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

    public Boolean isWin() {
        return win;
    }

    public Integer getKills() {
        return kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }
}
