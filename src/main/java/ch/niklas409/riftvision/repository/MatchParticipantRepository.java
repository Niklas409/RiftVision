package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.domain.entity.MatchEntity;
import ch.niklas409.riftvision.domain.entity.MatchParticipantEntity;
import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchParticipantRepository extends JpaRepository<MatchParticipantEntity, Long> {
    boolean existsByMatchAndPlayer(MatchEntity match, PlayerEntity player);
    List<MatchParticipantEntity> findByPlayer(PlayerEntity player);
}