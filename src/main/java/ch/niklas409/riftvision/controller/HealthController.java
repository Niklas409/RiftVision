package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.response.HealthResponse;
import ch.niklas409.riftvision.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ApiResponse<HealthResponse> getHealth() {
        return ApiResponse.success(healthService.getHealth());
    }
}