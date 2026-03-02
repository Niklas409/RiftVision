package ch.niklas409.riftvision.dto;

public class PlayerResponse {

    private String playerId;
    private String name;
    private String region;

    public PlayerResponse(String playerId, String name, String region) {
        this.playerId = playerId;
        this.name = name;
        this.region = region;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
