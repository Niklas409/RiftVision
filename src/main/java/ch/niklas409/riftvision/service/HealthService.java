package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.dto.response.HealthResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class HealthService {

    public HealthResponse getHealth() {
        return new HealthResponse("UP", "RiftVision", LocalDateTime.now());
    }

}
