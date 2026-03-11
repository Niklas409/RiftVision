package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.CoachClientRelationEntity;
import ch.niklas409.riftvision.domain.entity.NoteEntity;
import ch.niklas409.riftvision.domain.entity.Role;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.request.CreateNoteRequest;
import ch.niklas409.riftvision.dto.response.NoteResponse;
import ch.niklas409.riftvision.exception.CoachStudentRelationNotFoundException;
import ch.niklas409.riftvision.exception.InvalidCoachRoleException;
import ch.niklas409.riftvision.exception.ResourceNotFoundException;
import ch.niklas409.riftvision.repository.CoachClientRelationRepository;
import ch.niklas409.riftvision.repository.MatchRepository;
import ch.niklas409.riftvision.repository.NoteRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class NoteService {

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final NoteRepository noteRepository;
    private final CoachClientRelationRepository coachClientRelationRepository;

    public NoteService(UserRepository userRepository, MatchRepository matchRepository, NoteRepository noteRepository, CoachClientRelationRepository coachClientRelationRepository) {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
        this.noteRepository = noteRepository;
        this.coachClientRelationRepository = coachClientRelationRepository;
    }

    public NoteResponse createNote(String matchId, String coachEmail, CreateNoteRequest request) {
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("Coach hasn't Coach Role");
        }
        UserEntity student = userRepository.findById(request.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if(student.getRole().equals(Role.COACH) || student.getRole().equals(Role.ADMIN)) {
            throw new InvalidCoachRoleException("Student hasn't User Role");
        }
        Optional<CoachClientRelationEntity> relationEntity = coachClientRelationRepository.findByCoachAndStudent(coach, student);
        if(relationEntity.isEmpty()) {
            throw new CoachStudentRelationNotFoundException("No relationship was found between the student and the coach.");
        }
        if(!matchRepository.existsByMatchId(matchId)) {
            throw new ResourceNotFoundException("No Match with Id " + matchId + " found");
        }
        NoteEntity note = new NoteEntity(matchId, coach, student, request.getContent(), Instant.now());
        NoteEntity savedNote = noteRepository.save(note);
        return new NoteResponse(savedNote.getId(), savedNote.getMatchId(), savedNote.getContent(), savedNote.getCoach().getEmail(), savedNote.getStudent().getEmail(), savedNote.getCreatedAt());
    }

}
