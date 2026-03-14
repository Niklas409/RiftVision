package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matchId;

    @Column(nullable=false)
    private Instant playedAt;

    @OneToMany(mappedBy = "match")
    private List<MatchParticipantEntity> participants;

    protected MatchEntity() {
    }

    public MatchEntity(String matchId, Instant playedAt) {
        this.matchId = matchId;
        this.playedAt = playedAt;
    }

    public Long getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public List<MatchParticipantEntity> getParticipants() {
        return participants;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }

    public void setParticipants(List<MatchParticipantEntity> participants) {
        this.participants = participants;
    }
}
