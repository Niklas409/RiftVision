package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "coach_client_relations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"coach_id", "student_id"})
        }
)
public class CoachClientRelationEntity {

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
    private Instant createdAt;

    public CoachClientRelationEntity(UserEntity coach, UserEntity student, Instant createdAt) {
        this.coach = coach;
        this.student = student;
        this.createdAt = createdAt;
    }

    protected CoachClientRelationEntity() {
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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
