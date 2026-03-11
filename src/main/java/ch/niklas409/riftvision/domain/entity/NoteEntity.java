package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "notes")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String matchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", nullable = false)
    private UserEntity coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant createdAt;

    public NoteEntity(String matchId, UserEntity coach, UserEntity student, String content, Instant createdAt) {
        this.matchId = matchId;
        this.coach = coach;
        this.student = student;
        this.content = content;
        this.createdAt = createdAt;
    }

    protected NoteEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public UserEntity getCoach() {
        return coach;
    }

    public UserEntity getStudent() {
        return student;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setCoach(UserEntity coach) {
        this.coach = coach;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
