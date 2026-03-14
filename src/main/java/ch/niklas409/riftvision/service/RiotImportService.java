package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.client.riot.RiotApiClient;
import ch.niklas409.riftvision.domain.entity.MatchEntity;
import ch.niklas409.riftvision.domain.entity.MatchParticipantEntity;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.dto.response.ImportMatchesResponse;
import ch.niklas409.riftvision.dto.response.PlayerMatchStatsResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotAccountResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotMatchResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotParticipantResponse;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.repository.MatchParticipantRepository;
import ch.niklas409.riftvision.repository.MatchRepository;
import ch.niklas409.riftvision.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RiotImportService {

    private final RiotApiClient riotApiClient;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final MatchParticipantRepository matchParticipantRepository;

    public RiotImportService(RiotApiClient riotApiClient, MatchRepository matchRepository, PlayerRepository playerRepository, MatchParticipantRepository matchParticipantRepository) {
        this.riotApiClient = riotApiClient;
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.matchParticipantRepository = matchParticipantRepository;
    }

    public RiotAccountResponse getAccountByRiotId(String gameName, String tagLine) {
        return riotApiClient.getAccountByRiotId(gameName, tagLine);
    }

    public List<String> getRecentMatchIds(String gameName, String tagLine, int count) {
        RiotAccountResponse account = riotApiClient.getAccountByRiotId(gameName, tagLine);
        return riotApiClient.getMatchIdsByPuuid(account.getPuuid(), count);
    }
    public RiotMatchResponse getMatch(String matchId) {
        return riotApiClient.getMatchById(matchId);
    }

    public PlayerMatchStatsResponse getPlayerStatsFromMatch(String matchId, String puuid) {
        RiotMatchResponse match = getMatch(matchId);
        RiotParticipantResponse participant = match.getInfo().getParticipants()
                .stream()
                .filter(user -> user.getPuuid().equals(puuid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Participant not found in match"));
        return new PlayerMatchStatsResponse(match.getMetadata().getMatchId(),
                participant.getChampionName(),
                participant.getKills(),
                participant.getDeaths(),
                participant.getAssists(),
                participant.isWin(),
                match.getInfo().getGameCreation());
    }

    public List<PlayerMatchStatsResponse> getRecentMatchStats(String gameName, String tagLine, int count) {
        RiotAccountResponse account = getAccountByRiotId(gameName, tagLine);
        String puuid = account.getPuuid();
        List<String> matchIds = riotApiClient.getMatchIdsByPuuid(puuid, count);
        return matchIds.stream()
                .map(matchId -> getPlayerStatsFromMatch(matchId, puuid))
                .toList();
    }

    // TODO Phase 6.5:
    // MatchEntity global pro Riot-Match speichern
    // und MatchParticipantEntity pro (match, player) speichern.
    public ImportMatchesResponse importRecentMatches(String gameName, String tagLine, int count) {
        int skipped = 0;
        int imported = 0;
        RiotAccountResponse account = getAccountByRiotId(gameName, tagLine);
        String puuid = account.getPuuid();
        PlayerEntity player = getOrCreatePlayer(account);
        List<String> matchIds = riotApiClient.getMatchIdsByPuuid(puuid, count);
        for (String matchId : matchIds) {
            if(importMatchIfNotExists(player, puuid, matchId)) {
                imported++;
            } else {
                skipped++;
            }
        }
        return new ImportMatchesResponse(imported, skipped);
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

    private MatchEntity getOrCreateMatch(PlayerMatchStatsResponse stats) {
        return matchRepository
                .findByMatchId(stats.getMatchId())
                .orElseGet(() -> matchRepository.save(
                        new MatchEntity(
                                stats.getMatchId(),
                                Instant.ofEpochMilli(stats.getPlayedAt())
                        )
                ));
    }

    private boolean importMatchIfNotExists(PlayerEntity player, String puuid, String matchId) {
        if (matchRepository.existsByMatchId(matchId)) {
            return false;
        }
        PlayerMatchStatsResponse stats = getPlayerStatsFromMatch(matchId, puuid);
        MatchEntity matchEntity = getOrCreateMatch(stats);
        matchRepository.save(matchEntity);
        MatchParticipantEntity participant = toMatchParticipantEntity(matchEntity, player, stats);
        if (!matchParticipantRepository.existsByMatchAndPlayer(matchEntity, player)) {
            matchParticipantRepository.save(participant);
        }
        return true;
    }

    private MatchEntity toMatchEntity(PlayerMatchStatsResponse stats) {
        return new MatchEntity(stats.getMatchId(),
                Instant.ofEpochMilli(stats.getPlayedAt()));
    }

    private MatchParticipantEntity toMatchParticipantEntity(MatchEntity match, PlayerEntity player, PlayerMatchStatsResponse stats) {
        return new MatchParticipantEntity(match, player, stats.getChampion(), stats.getKills(), stats.getDeaths(), stats.getAssists(), stats.isWin());
    }

}
