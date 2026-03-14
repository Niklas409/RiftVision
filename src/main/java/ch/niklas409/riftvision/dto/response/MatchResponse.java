package ch.niklas409.riftvision.dto.response;

import java.time.Instant;

public class MatchResponse {

    private Long id;
    private String matchId;
    private Instant playedAt;

    public MatchResponse(Long id, String matchId, Instant playedAt) {
        this.id = id;
        this.matchId = matchId;
        this.playedAt = playedAt;
    }

    public Long getId() {
        return id;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }
}
