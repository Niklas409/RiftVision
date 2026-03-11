package ch.niklas409.riftvision.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateNoteRequest {

    @NotBlank
    private String content;

    @NotBlank
    private Long studentId;

    public CreateNoteRequest(String content, Long studentId) {
        this.content = content;
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
