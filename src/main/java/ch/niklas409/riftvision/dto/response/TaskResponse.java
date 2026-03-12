package ch.niklas409.riftvision.dto.response;

import java.time.Instant;

public class TaskResponse {

    private long id;
    private String description;
    private boolean completed;
    private String coachEmail;
    private String studentEmail;
    private Instant createdAt;

    public TaskResponse(long id, String description, boolean completed, String coachEmail, String studentEmail, Instant createdAt) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.coachEmail = coachEmail;
        this.studentEmail = studentEmail;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getCoachEmail() {
        return coachEmail;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCoachEmail(String coachEmail) {
        this.coachEmail = coachEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
