package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.client.riot.RiotApiClient;
import ch.niklas409.riftvision.domain.entity.MatchEntity;
import ch.niklas409.riftvision.domain.entity.MatchParticipantEntity;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.response.ImportMatchesResponse;
import ch.niklas409.riftvision.dto.response.PlayerMatchStatsResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotAccountResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotMatchResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotParticipantResponse;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.repository.MatchParticipantRepository;
import ch.niklas409.riftvision.repository.MatchRepository;
import ch.niklas409.riftvision.repository.PlayerRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RiotImportService {

    private final RiotApiClient riotApiClient;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final MatchParticipantRepository matchParticipantRepository;
    private final UserRepository userRepository;

    public RiotImportService(RiotApiClient riotApiClient, MatchRepository matchRepository, PlayerRepository playerRepository, MatchParticipantRepository matchParticipantRepository, UserRepository userRepository) {
        this.riotApiClient = riotApiClient;
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.matchParticipantRepository = matchParticipantRepository;
        this.userRepository = userRepository;
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
                Instant.ofEpochMilli(match.getInfo().getGameCreation()));
    }

    public List<PlayerMatchStatsResponse> getRecentMatchStats(String gameName, String tagLine, int count) {
        RiotAccountResponse account = getAccountByRiotId(gameName, tagLine);
        String puuid = account.getPuuid();
        List<String> matchIds = riotApiClient.getMatchIdsByPuuid(puuid, count);
        return matchIds.stream()
                .map(matchId -> getPlayerStatsFromMatch(matchId, puuid))
                .toList();
    }

    public ImportMatchesResponse importRecentMatches(String gameName, String tagLine, int count) {
        int skipped = 0;
        int imported = 0;
        RiotAccountResponse account = getAccountByRiotId(gameName, tagLine);
        String puuid = account.getPuuid();
        PlayerEntity player = getOrCreatePlayer(account);
        if(player.getUser() == null) {
            UserEntity user = getCurrentUser();
            player.setUser(user);
            playerRepository.save(player);
        }
        List<String> matchIds = riotApiClient.getMatchIdsByPuuid(puuid, count);
        for (String matchId : matchIds) {
            if(importMatchIfNotExists(puuid, matchId)) {
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
                                stats.getPlayedAt()
                        )
                ));
    }

    private boolean importMatchIfNotExists(String puuid, String matchId) {
        if (matchRepository.existsByMatchId(matchId)) {
            return false;
        }
        RiotMatchResponse riotMatch = getMatch(matchId);
        PlayerMatchStatsResponse stats = getPlayerStatsFromMatch(matchId, puuid);
        MatchEntity matchEntity = getOrCreateMatch(stats);
        importParticipants(matchEntity, riotMatch);
        return true;
    }

    private MatchEntity toMatchEntity(PlayerMatchStatsResponse stats) {
        return new MatchEntity(stats.getMatchId(),
                stats.getPlayedAt());
    }

    private MatchParticipantEntity toMatchParticipantEntity(MatchEntity match, PlayerEntity player, PlayerMatchStatsResponse stats) {
        return new MatchParticipantEntity(match, player, stats.getChampion(), stats.getKills(), stats.getDeaths(), stats.getAssists(), stats.isWin());
    }

    private void importParticipants(MatchEntity matchEntity, RiotMatchResponse riotMatch) {
        for(RiotParticipantResponse riotParticipantResponse : riotMatch.getInfo().getParticipants()) {
            PlayerEntity player = getOrCreatePlayer(
                    riotParticipantResponse.getPuuid(),
                    riotParticipantResponse.getRiotIdGameName(),
                    riotParticipantResponse.getRiotIdTagline()
            );
            MatchParticipantEntity participant = new MatchParticipantEntity(matchEntity,
                    player,
                    riotParticipantResponse.getChampionName(),
                    riotParticipantResponse.getKills(),
                    riotParticipantResponse.getDeaths(),
                    riotParticipantResponse.getAssists(),
                    riotParticipantResponse.isWin());
            matchParticipantRepository.save(participant);
        }
    }

    private PlayerEntity getOrCreatePlayer(String puuid, String gameName, String tagLine) {
        return playerRepository.findByPlayerId(puuid)
                .orElseGet(() -> playerRepository.save(
                        new PlayerEntity(
                                puuid,
                                gameName != null ? gameName : "Unknown",
                                tagLine != null ? tagLine : "XX"
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
