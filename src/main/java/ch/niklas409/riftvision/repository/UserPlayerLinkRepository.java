package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.domain.entity.PlayerEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.domain.entity.UserPlayerLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPlayerLinkRepository extends JpaRepository<UserPlayerLinkEntity, Long> {

    List<UserPlayerLinkEntity> findAllByUser(UserEntity user);
    Optional<UserPlayerLinkEntity> findByUserAndPlayer(UserEntity user, PlayerEntity player);
    boolean existsByUserAndPlayer(UserEntity user, PlayerEntity player);

}
