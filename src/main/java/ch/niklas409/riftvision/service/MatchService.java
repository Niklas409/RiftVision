package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.dto.request.MatchRequest;
import ch.niklas409.riftvision.dto.response.MatchResponse;
import ch.niklas409.riftvision.dto.response.PlayerStatsResponse;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.mapper.MatchMapper;
import ch.niklas409.riftvision.domain.entity.MatchEntity;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.repository.MatchRepository;
import ch.niklas409.riftvision.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    public MatchService(PlayerRepository playerRepository, MatchRepository matchRepository, MatchMapper matchMapper) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    public MatchResponse createMatch(MatchRequest request) {
        PlayerEntity player = playerRepository.findByPlayerId(request.getPlayerId()).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        MatchEntity match = new MatchEntity(player, request.getChampion(), request.isWin(), request.getKills(), request.getDeaths(), request.getAssists(), request.getPlayedAt());
        return matchMapper.toResponse(matchRepository.save(match));
    }

    public List<MatchResponse> getAllMatches() {
        return matchRepository.findAll().stream().map(matchMapper::toResponse).toList();
    }

    public PlayerStatsResponse calculateStats(String playerId) {
        PlayerEntity player = playerRepository.findByPlayerId(playerId).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        List<MatchEntity> matches = matchRepository.findByPlayer(player);
        if(matches.isEmpty()) {
            throw new ResourceNotFoundException("No matches found for playerId: " + playerId);
        }
        int matchCount = matches.size();
        int wins = (int) matches.stream().filter(MatchEntity::isWin).count();
        int losses = matchCount - wins;
        int kills = matches.stream().mapToInt(MatchEntity::getKills).sum();
        int deaths = matches.stream().mapToInt(MatchEntity::getDeaths).sum();
        int assists = matches.stream().mapToInt(MatchEntity::getAssists).sum();
        double rawKda = (kills + assists) / (double) Math.max(1, deaths);
        double kda = Math.round(rawKda * 100.0) / 100.0;
        return new PlayerStatsResponse(playerId, matchCount, wins, losses, kills, deaths, assists, kda);
    }

}
