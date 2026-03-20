package ch.niklas409.riftvision.dto.riot.response;

public class RiotParticipantResponse {

    private String puuid;
    private String riotIdGameName;
    private String riotIdTagline;
    private String championName;
    private int kills;
    private int deaths;
    private int assists;
    private boolean win;
    private int teamId;

    public String getPuuid() {
        return puuid;
    }

    public String getRiotIdGameName() {
        return riotIdGameName;
    }

    public String getRiotIdTagline() {
        return riotIdTagline;
    }

    public String getChampionName() {
        return championName;
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

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public void setRiotIdGameName(String riotIdGameName) {
        this.riotIdGameName = riotIdGameName;
    }

    public void setRiotIdTagline(String riotIdTagline) {
        this.riotIdTagline = riotIdTagline;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
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
