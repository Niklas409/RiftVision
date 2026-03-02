package ch.niklas409.riftvision.dto;

public class PlayerStatsResponse {

    private String playerId;
    private int matches;
    private int wins;
    private int losses;
    private int kills;
    private int deaths;
    private int assists;
    private double kda;

    public PlayerStatsResponse(String playerId, int matches, int wins, int losses, int kills, int deaths, int assists, double kda) {
        this.playerId = playerId;
        this.matches = matches;
        this.wins = wins;
        this.kills = kills;
        this.losses = losses;
        this.deaths = deaths;
        this.assists = assists;
        this.kda = kda;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getMatches() {
        return matches;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
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

    public double getKda() {
        return kda;
    }
}
