package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.dto.PlayerRequest;
import ch.niklas409.riftvision.dto.PlayerResponse;
import ch.niklas409.riftvision.mapper.PlayerMapper;
import ch.niklas409.riftvision.model.entity.PlayerEntity;
import ch.niklas409.riftvision.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public PlayerResponse createPlayer(PlayerRequest request) {
        PlayerEntity playerEntity = new PlayerEntity(request.getPlayerId(), request.getName(), request.getRegion());
        return playerMapper.toResponse(playerRepository.save(playerEntity));
    }

}
