package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.MatchRequest;
import ch.niklas409.riftvision.dto.PlayerStatsResponse;
import ch.niklas409.riftvision.model.Match;
import ch.niklas409.riftvision.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMatch(@Valid @RequestBody MatchRequest request) {
        Match match = new Match(
                request.getPlayerId(),
                request.getChampion(),
                request.getWin(),
                request.getKills(),
                request.getDeaths(),
                request.getAssists(),
                request.getPlayedAt()
        );
        matchService.addMatch(match);
    }

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

}
