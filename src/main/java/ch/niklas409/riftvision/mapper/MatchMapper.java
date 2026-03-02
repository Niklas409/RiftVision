package ch.niklas409.riftvision.mapper;

import ch.niklas409.riftvision.dto.MatchRequest;
import ch.niklas409.riftvision.model.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    public Match toModel(MatchRequest request) {
        return new Match(
                request.getPlayerId(),
                request.getChampion(),
                request.getWin(),
                request.getKills(),
                request.getDeaths(),
                request.getAssists(),
                request.getPlayedAt()
        );
    }

}
