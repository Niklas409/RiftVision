package ch.niklas409.riftvision.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateNoteRequest {

    @NotBlank
    private String content;

    public UpdateNoteRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
