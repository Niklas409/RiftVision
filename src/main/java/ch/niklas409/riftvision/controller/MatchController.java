package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.request.MatchRequest;
import ch.niklas409.riftvision.dto.response.MatchResponse;
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
    public ApiResponse<MatchResponse> createMatch(@Valid @RequestBody MatchRequest request) {
        return ApiResponse.success(matchService.createMatch(request));
    }

    @GetMapping
    public ApiResponse<List<MatchResponse>> getAllMatches() {
        return ApiResponse.success(matchService.getAllMatches());
    }

}
