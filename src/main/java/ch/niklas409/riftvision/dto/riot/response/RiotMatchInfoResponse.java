package ch.niklas409.riftvision.dto.riot.response;

import java.util.List;

public class RiotMatchInfoResponse {

    private long gameCreation;
    private List<RiotParticipantResponse> participants;

    public long getGameCreation() {
        return gameCreation;
    }

    public List<RiotParticipantResponse> getParticipants() {
        return participants;
    }

    public void setGameCreation(long gameCreation) {
        this.gameCreation = gameCreation;
    }

    public void setParticipants(List<RiotParticipantResponse> participants) {
        this.participants = participants;
    }
}
