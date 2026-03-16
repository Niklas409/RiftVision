package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    Optional<PlayerEntity> findByPlayerId(String playerId);
    List<PlayerEntity> findAllByUser(UserEntity user);

}
