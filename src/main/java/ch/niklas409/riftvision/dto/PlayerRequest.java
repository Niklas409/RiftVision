package ch.niklas409.riftvision.dto;

import jakarta.validation.constraints.NotBlank;

public class PlayerRequest {

    @NotBlank
    private String playerId;

    @NotBlank
    private String name;

    @NotBlank
    private String region;

    public PlayerRequest(String playerId, String name, String region) {
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
