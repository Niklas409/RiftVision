package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/admin")
public class InternalAdminController {

    private final AuthService authService;

    public InternalAdminController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/users/{userId}/make-coach")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> makeCoach(@PathVariable Long userId) {
        authService.makeCoach(userId);
        return ApiResponse.success(null);
    }

}
