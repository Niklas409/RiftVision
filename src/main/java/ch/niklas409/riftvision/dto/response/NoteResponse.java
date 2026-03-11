package ch.niklas409.riftvision.dto.response;

import java.time.Instant;

public class NoteResponse {

    private long id;
    private String matchId;
    private String content;
    private String coachEmail;
    private String studentEmail;
    private Instant createdAt;

    public NoteResponse(long id, String matchId, String content, String coachEmail, String studentEmail, Instant createdAt) {
        this.id = id;
        this.matchId = matchId;
        this.content = content;
        this.coachEmail = coachEmail;
        this.studentEmail = studentEmail;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getContent() {
        return content;
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

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setContent(String content) {
        this.content = content;
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
