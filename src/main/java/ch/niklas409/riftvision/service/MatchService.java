package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.MatchParticipantEntity;
import ch.niklas409.riftvision.dto.request.MatchRequest;
import ch.niklas409.riftvision.dto.response.MatchResponse;
import ch.niklas409.riftvision.dto.response.PlayerStatsResponse;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.mapper.MatchMapper;
import ch.niklas409.riftvision.domain.entity.MatchEntity;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.repository.MatchParticipantRepository;
import ch.niklas409.riftvision.repository.MatchRepository;
import ch.niklas409.riftvision.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MatchService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;
    private final MatchParticipantRepository matchParticipantRepository;

    public MatchService(PlayerRepository playerRepository, MatchRepository matchRepository, MatchMapper matchMapper, MatchParticipantRepository matchParticipantRepository) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
        this.matchParticipantRepository = matchParticipantRepository;
    }
    // LEGACY (Phase 1/2):
    // Manueller Match-Create aus dem ursprünglichen lokalen MVP.
    // Wird aktuell nicht mehr aktiv genutzt.
    // Neue Matches werden über RiotImportService importiert.
    public MatchResponse createMatch(MatchRequest request) {
        PlayerEntity player = playerRepository.findByPlayerId(request.getPlayerId()).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        MatchEntity match = new MatchEntity(null, Instant.now());
        return matchMapper.toResponse(matchRepository.save(match));
    }

    public List<MatchResponse> getAllMatches() {
        return matchRepository.findAll().stream().map(matchMapper::toResponse).toList();
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

}
