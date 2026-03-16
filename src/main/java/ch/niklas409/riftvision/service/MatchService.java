package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.MatchParticipantEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.request.MatchRequest;
import ch.niklas409.riftvision.dto.response.MatchDetailsResponse;
import ch.niklas409.riftvision.dto.response.MatchParticipantResponse;
import ch.niklas409.riftvision.dto.response.MatchResponse;
import ch.niklas409.riftvision.dto.response.PlayerStatsResponse;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.mapper.MatchMapper;
import ch.niklas409.riftvision.domain.entity.MatchEntity;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.repository.MatchParticipantRepository;
import ch.niklas409.riftvision.repository.MatchRepository;
import ch.niklas409.riftvision.repository.PlayerRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;
    private final MatchParticipantRepository matchParticipantRepository;
    private final UserRepository userRepository;

    public MatchService(PlayerRepository playerRepository, MatchRepository matchRepository, MatchMapper matchMapper, MatchParticipantRepository matchParticipantRepository, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
        this.matchParticipantRepository = matchParticipantRepository;
        this.userRepository = userRepository;
    }
    // LEGACY (Phase 1/2):
    // Manueller Match-Create aus dem ursprünglichen lokalen MVP.
    // Wird aktuell nicht mehr aktiv genutzt.
    // Neue Matches werden über RiotImportService importiert.
    public MatchResponse createMatch(MatchRequest request) {
        throw new UnsupportedOperationException("Manual match creation is deprecated after phase 6.5");
    }

    public List<MatchResponse> getAllMatches() {
        List<MatchEntity> matches = new ArrayList<>();
        List<PlayerEntity> players = getCurrentUserPlayers();
        for(PlayerEntity player : players) {
            for(MatchParticipantEntity participant : matchParticipantRepository.findByPlayer(player)) {
                if(!matches.contains(participant.getMatch())) {
                    matches.add(participant.getMatch());
                }
            }
        }
        return matches.stream().map(matchMapper::toResponse).toList();
    }

    public PlayerStatsResponse calculateStats(String playerId) {
        PlayerEntity player = playerRepository.findByPlayerId(playerId).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        List<MatchParticipantEntity> participants = matchParticipantRepository.findByPlayer(player);
        if(participants.isEmpty()) {
            throw new ResourceNotFoundException("No matches found for playerId: " + playerId);
        }
        int matchCount = participants.size();
        int wins = (int) participants.stream().filter(MatchParticipantEntity::isWin).count();
        int losses = matchCount - wins;
        int kills = participants.stream().mapToInt(MatchParticipantEntity::getKills).sum();
        int deaths = participants.stream().mapToInt(MatchParticipantEntity::getDeaths).sum();
        int assists = participants.stream().mapToInt(MatchParticipantEntity::getAssists).sum();
        double rawKda = (kills + assists) / (double) Math.max(1, deaths);
        double kda = Math.round(rawKda * 100.0) / 100.0;
        return new PlayerStatsResponse(playerId, matchCount, wins, losses, kills, deaths, assists, kda);
    }

    @Transactional
    public MatchDetailsResponse getMatchByMatchId(String matchId) {
        List<MatchParticipantResponse> participants = new ArrayList<>();
        MatchEntity match = matchRepository.findByMatchId(matchId).orElseThrow(() -> new ResourceNotFoundException("Match not found"));
        for(MatchParticipantEntity participant : match.getParticipants()) {
            participants.add(new MatchParticipantResponse(participant.getPlayer().getPlayerId(),
                    participant.getPlayer().getName(),
                    participant.getChampion(),
                    participant.getKills(),
                    participant.getDeaths(),
                    participant.getAssists(),
                    participant.isWin()));
        }
        return new MatchDetailsResponse(match.getMatchId(),
                match.getPlayedAt(),
                participants);
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

    private List<PlayerEntity> getCurrentUserPlayers() {
        UserEntity user = getCurrentUser();
        return playerRepository.findAllByUser(user);
    }

}
