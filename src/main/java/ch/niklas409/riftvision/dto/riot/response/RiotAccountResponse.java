package ch.niklas409.riftvision.dto.riot.response;

public class RiotAccountResponse {

    private String puuid;
    private String gameName;
    private String tagLine;

    public RiotAccountResponse() {
    }

    public String getPuuid() {
        return puuid;
    }

    public String getGameName() {
        return gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }
}
