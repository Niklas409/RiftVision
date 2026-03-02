package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.MatchRequest;
import ch.niklas409.riftvision.dto.PlayerStatsResponse;
import ch.niklas409.riftvision.mapper.MatchMapper;
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
    private final MatchMapper matchMapper;

    public MatchController(MatchService matchService, MatchMapper matchMapper) {
        this.matchService = matchService;
        this.matchMapper = matchMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createMatch(@Valid @RequestBody MatchRequest request) {
        matchService.addMatch(matchMapper.toModel(request));
        return new ApiResponse<>("Match created", null);
    }

    @GetMapping
    public ApiResponse<List<Match>> getAllMatches() {
        return new ApiResponse<>("OK", matchService.getAllMatches());
    }

}
