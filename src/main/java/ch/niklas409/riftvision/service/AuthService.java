package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.Role;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.request.LoginRequest;
import ch.niklas409.riftvision.dto.response.AuthResponse;
import ch.niklas409.riftvision.dto.request.RegisterRequest;
import ch.niklas409.riftvision.exception.InvalidCredentialsException;
import ch.niklas409.riftvision.repository.UserRepository;
import ch.niklas409.riftvision.security.JwtService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity userEntity = new UserEntity(email, hashedPassword, Role.USER);
        userRepository.save(userEntity);
        return new AuthResponse("User registered successfully", null);
    }

    public AuthResponse login(LoginRequest request) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        if(!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String token = jwtService.generateToken(userEntity.getEmail());
        return new AuthResponse("Login successful", token);
    }
}
