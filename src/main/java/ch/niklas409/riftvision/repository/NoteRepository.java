package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.domain.entity.NoteEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository  extends JpaRepository<NoteEntity, Long> {

    Optional<UserEntity> findByMatchId(String noteId);

}
