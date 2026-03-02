package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.model.entity.MatchEntity;
import ch.niklas409.riftvision.model.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    List<MatchEntity> findByPlayer(PlayerEntity player);

}
