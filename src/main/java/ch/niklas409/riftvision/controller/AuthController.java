package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.request.LoginRequest;
import ch.niklas409.riftvision.dto.response.AuthResponse;
import ch.niklas409.riftvision.dto.request.RegisterRequest;
import ch.niklas409.riftvision.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

}
