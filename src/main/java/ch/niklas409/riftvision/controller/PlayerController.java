package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.PlayerRequest;
import ch.niklas409.riftvision.dto.PlayerResponse;
import ch.niklas409.riftvision.dto.PlayerStatsResponse;
import ch.niklas409.riftvision.model.entity.PlayerEntity;
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
    public ApiResponse<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest request) {
        return ApiResponse.success(playerService.createPlayer(request));
    }

}
