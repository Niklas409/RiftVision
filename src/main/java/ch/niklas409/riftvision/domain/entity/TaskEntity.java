package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", nullable = false)
    private UserEntity coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserEntity student;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private Instant createdAt;

    public TaskEntity(UserEntity coach, UserEntity student, String description, boolean completed, Instant createdAt) {
        this.coach = coach;
        this.student = student;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    protected TaskEntity() {
    }

    public Long getId() {
        return id;
    }

    public UserEntity getCoach() {
        return coach;
    }

    public UserEntity getStudent() {
        return student;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCoach(UserEntity coach) {
        this.coach = coach;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
