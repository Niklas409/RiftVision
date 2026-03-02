package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.PlayerStatsResponse;
import ch.niklas409.riftvision.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final MatchService matchService;

    public PlayerController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{playerId}/stats")
    public PlayerStatsResponse getStats(@PathVariable String playerId) {
        return matchService.calculateStats(playerId);
    }

}
