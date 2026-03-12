package ch.niklas409.riftvision.repository;

import ch.niklas409.riftvision.domain.entity.NoteEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    List<NoteEntity> findByCoachAndStudentAndMatchId(UserEntity coach, UserEntity student, String matchId);
    void delete(NoteEntity note);

}
