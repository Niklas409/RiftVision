package ch.niklas409.riftvision.mapper;

import ch.niklas409.riftvision.dto.PlayerResponse;
import ch.niklas409.riftvision.model.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerResponse toResponse(PlayerEntity player) {
        return new PlayerResponse(player.getPlayerId(), player.getName(), player.getRegion());
    }

}
