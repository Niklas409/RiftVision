package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.model.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    Optional<PlayerEntity> findByPlayerId(String playerId);

}
