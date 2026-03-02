package ch.niklas409.riftvision.mapper;

import ch.niklas409.riftvision.dto.MatchResponse;
import ch.niklas409.riftvision.model.entity.MatchEntity;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    public MatchResponse toResponse(MatchEntity entity) {
        return new MatchResponse(entity.getId(), entity.getPlayer().getPlayerId(), entity.getChampion(), entity.isWin(), entity.getKills(), entity.getDeaths(), entity.getAssists(), entity.getPlayedAt());
    }

}
