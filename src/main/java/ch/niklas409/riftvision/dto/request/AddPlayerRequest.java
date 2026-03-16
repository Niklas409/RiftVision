package ch.niklas409.riftvision.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AddPlayerRequest {

    @NotBlank
    private String gameName;

    @NotBlank
    private String tagLine;

    public AddPlayerRequest(String gameName, String tagLine) {
        this.gameName = gameName;
        this.tagLine = tagLine;
    }

    public String getGameName() {
        return gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }
}
