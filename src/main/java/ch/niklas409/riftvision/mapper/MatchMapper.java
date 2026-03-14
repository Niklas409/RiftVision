package ch.niklas409.riftvision.mapper;

import ch.niklas409.riftvision.dto.response.MatchResponse;
import ch.niklas409.riftvision.domain.entity.MatchEntity;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    public MatchResponse toResponse(MatchEntity entity) {
        return new MatchResponse(
                entity.getId(),
                entity.getMatchId(),
                entity.getPlayedAt()
        );
    }

}
