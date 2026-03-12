package ch.niklas409.riftvision.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateTaskRequest {

    @NotBlank
    private String description;

    public UpdateTaskRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
