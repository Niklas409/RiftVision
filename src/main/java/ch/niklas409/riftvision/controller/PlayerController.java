package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.request.AddPlayerRequest;
import ch.niklas409.riftvision.dto.request.MatchRequest;
import ch.niklas409.riftvision.dto.request.PlayerRequest;
import ch.niklas409.riftvision.dto.response.MatchResponse;
import ch.niklas409.riftvision.dto.response.PlayerResponse;
import ch.niklas409.riftvision.dto.response.PlayerStatsResponse;
import ch.niklas409.riftvision.service.MatchService;
import ch.niklas409.riftvision.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final MatchService matchService;
    private final PlayerService playerService;

    public PlayerController(MatchService matchService, PlayerService playerService) {
        this.matchService = matchService;
        this.playerService = playerService;
    }

    @GetMapping("/{playerId}/stats")
    public ApiResponse<PlayerStatsResponse> getStats(@PathVariable String playerId) {
        return ApiResponse.success(matchService.calculateStats(playerId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PlayerResponse> linkAccount(@Valid @RequestBody AddPlayerRequest request) {
        return ApiResponse.success(playerService.addPlayerToUser(request));
    }

}
