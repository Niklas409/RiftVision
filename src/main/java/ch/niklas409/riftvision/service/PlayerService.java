package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.domain.entity.UserPlayerLinkEntity;
import ch.niklas409.riftvision.dto.request.AddPlayerRequest;
import ch.niklas409.riftvision.dto.request.PlayerRequest;
import ch.niklas409.riftvision.dto.response.PlayerResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotAccountResponse;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.mapper.PlayerMapper;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.repository.PlayerRepository;
import ch.niklas409.riftvision.repository.UserPlayerLinkRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final RiotImportService riotImportService;
    private final UserRepository userRepository;
    private final UserPlayerLinkRepository userPlayerLinkRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper, RiotImportService riotImportService, UserRepository userRepository, UserPlayerLinkRepository userPlayerLinkRepository) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.riotImportService = riotImportService;
        this.userRepository = userRepository;
        this.userPlayerLinkRepository = userPlayerLinkRepository;
    }

    public PlayerResponse createPlayer(PlayerRequest request) {
        PlayerEntity playerEntity = new PlayerEntity(request.getPlayerId(), request.getName(), request.getRegion());
        return playerMapper.toResponse(playerRepository.save(playerEntity));
    }

    public PlayerResponse addPlayerToUser(AddPlayerRequest request) {
        RiotAccountResponse accountResponse = riotImportService.getAccountByRiotId(request.getGameName(), request.getTagLine());
        PlayerEntity player = getOrCreatePlayer(accountResponse);
        UserEntity user = getCurrentUser();
        if(!userPlayerLinkRepository.existsByUserAndPlayer(user, player)) {
            userPlayerLinkRepository.save(new UserPlayerLinkEntity(user, player, Instant.now()));
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

    public List<PlayerResponse> getLinkedPlayersForCurrentUser() {
        List<PlayerResponse> playerResponses = new ArrayList<>();
        UserEntity user = getCurrentUser();
        List<UserPlayerLinkEntity> links = userPlayerLinkRepository.findAllByUser(user);
        for(UserPlayerLinkEntity userPlayerLink : links) {
            playerResponses.add(playerMapper.toResponse(userPlayerLink.getPlayer()));
        }
        return playerResponses;
    }

    public void removePlayerFromCurrentUser(String playerId) {
        UserEntity user = getCurrentUser();
        PlayerEntity player = playerRepository.findByPlayerId(playerId).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        UserPlayerLinkEntity playerLinkEntity = userPlayerLinkRepository.findByUserAndPlayer(user, player).orElseThrow(() -> new ResourceNotFoundException("Player link not found"));
        userPlayerLinkRepository.delete(playerLinkEntity);
    }

}
