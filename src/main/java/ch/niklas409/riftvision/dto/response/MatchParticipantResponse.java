package ch.niklas409.riftvision.dto.response;

public class MatchParticipantResponse {

    private String playerId;
    private String playerName;
    private String champion;
    private int kills;
    private int deaths;
    private int assists;
    private boolean win;
    private int teamId;

    public MatchParticipantResponse(String playerId, String playerName, String champion, int kills, int deaths, int assists, boolean win, int teamId) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.champion = champion;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.win = win;
        this.teamId = teamId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
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

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
}
