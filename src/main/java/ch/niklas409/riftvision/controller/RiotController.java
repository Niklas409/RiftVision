package ch.niklas409.riftvision.controller;
import ch.niklas409.riftvision.domain.entity.MatchEntity;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.request.MatchRequest;
import ch.niklas409.riftvision.dto.response.ImportMatchesResponse;
import ch.niklas409.riftvision.dto.response.MatchResponse;
import ch.niklas409.riftvision.dto.response.PlayerMatchStatsResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotAccountResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotMatchResponse;
import ch.niklas409.riftvision.dto.riot.response.RiotParticipantResponse;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.service.RiotImportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/riot")
public class RiotController {

    private final RiotImportService riotImportService;

    public RiotController(RiotImportService riotImportService) {
        this.riotImportService = riotImportService;
    }

    @GetMapping("/{gameName}/{tagLine}")
    public ApiResponse<RiotAccountResponse> getAccountByRiotId(@PathVariable String gameName, @PathVariable String tagLine) {
        return ApiResponse.success(riotImportService.getAccountByRiotId(gameName, tagLine));
    }

    @GetMapping("/matches/{gameName}/{tagLine}")
    public ApiResponse<List<String>> getMatches(@PathVariable String gameName, @PathVariable String tagLine) {
        return ApiResponse.success(riotImportService.getRecentMatchIds(gameName, tagLine, 5));
    }

    @GetMapping("/match/{matchId}")
    public ApiResponse<RiotMatchResponse> getMatch(@PathVariable String matchId) {
        return ApiResponse.success(riotImportService.getMatch(matchId));
    }

    @GetMapping("/stats/{matchId}/{puuid}")
    public ApiResponse<PlayerMatchStatsResponse> getStats(@PathVariable String matchId, @PathVariable String puuid) {
        return ApiResponse.success(riotImportService.getPlayerStatsFromMatch(matchId, puuid));
    }

    @GetMapping("/recent-stats/{gameName}/{tagLine}")
    public ApiResponse<List<PlayerMatchStatsResponse>> getRecentStats(@PathVariable String gameName, @PathVariable String tagLine) {
        return ApiResponse.success(riotImportService.getRecentMatchStats(gameName, tagLine, 5));
    }

    @PostMapping("/import/{gameName}/{tagLine}")
    public ApiResponse<ImportMatchesResponse> importMatches(@PathVariable String gameName, @PathVariable String tagLine, @RequestParam(defaultValue = "5") int count) {
        return ApiResponse.success(riotImportService.importRecentMatches(gameName, tagLine, count));
    }
}