package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.request.AddPlayerRequest;
import ch.niklas409.riftvision.dto.request.PlayerRequest;
import ch.niklas409.riftvision.dto.response.PlayerResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotAccountResponse;
import ch.niklas409.riftvision.exception.PlayerAlreadyAssignedException;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.mapper.PlayerMapper;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.repository.PlayerRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final RiotImportService riotImportService;
    private final UserRepository userRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper, RiotImportService riotImportService, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.riotImportService = riotImportService;
        this.userRepository = userRepository;
    }

    public PlayerResponse createPlayer(PlayerRequest request) {
        PlayerEntity playerEntity = new PlayerEntity(request.getPlayerId(), request.getName(), request.getRegion());
        return playerMapper.toResponse(playerRepository.save(playerEntity));
    }

    public PlayerResponse addPlayerToUser(AddPlayerRequest request) {
        RiotAccountResponse accountResponse = riotImportService.getAccountByRiotId(request.getGameName(), request.getTagLine());
        PlayerEntity player = getOrCreatePlayer(accountResponse);
        UserEntity user = getCurrentUser();
        if(player.getUser() == null) {
            player.setUser(user);
            playerRepository.save(player);
        } else if(!player.getUser().getId().equals(user.getId())) {
            throw new PlayerAlreadyAssignedException("This Riot account is already linked to another user.");
        }
        return playerMapper.toResponse(player);
    }

    private PlayerEntity getOrCreatePlayer(RiotAccountResponse account) {
        return playerRepository.findByPlayerId(account.getPuuid())
                .orElseGet(() -> playerRepository.save(
                        new PlayerEntity(
                                account.getPuuid(),
                                account.getGameName(),
                                account.getTagLine()
                        )
                ));
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private UserEntity getCurrentUser() {
        return getUserByEmail(getCurrentUserEmail());
    }

}
