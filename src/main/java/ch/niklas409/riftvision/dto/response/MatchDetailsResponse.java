package ch.niklas409.riftvision.dto.response;

import java.time.Instant;
import java.util.List;

public class MatchDetailsResponse {

    private String matchId;
    private Instant playedAt;
    private List<MatchParticipantResponse> participants;

    public MatchDetailsResponse(String matchId, Instant playedAt, List<MatchParticipantResponse> participants) {
        this.matchId = matchId;
        this.playedAt = playedAt;
        this.participants = participants;
    }

    public String getMatchId() {
        return matchId;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public List<MatchParticipantResponse> getParticipants() {
        return participants;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }

    public void setParticipants(List<MatchParticipantResponse> participants) {
        this.participants = participants;
    }
}
