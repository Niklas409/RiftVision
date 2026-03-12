package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.domain.entity.TaskEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByCoachAndStudent(UserEntity coach, UserEntity student);

}
