package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.domain.entity.CoachClientRelationEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoachClientRelationRepository extends JpaRepository<CoachClientRelationEntity, Long> {

    boolean existsByCoachAndStudent(UserEntity coach, UserEntity student);
    List<CoachClientRelationEntity> findByCoach(UserEntity coach);
    List<CoachClientRelationEntity> findByStudent(UserEntity student);
    Optional<CoachClientRelationEntity> findByCoachAndStudent(UserEntity coach, UserEntity student);
    void delete(CoachClientRelationEntity relation);

}
