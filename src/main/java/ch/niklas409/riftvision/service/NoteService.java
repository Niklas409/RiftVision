package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.NoteEntity;
import ch.niklas409.riftvision.domain.entity.Role;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.request.CreateNoteRequest;
import ch.niklas409.riftvision.dto.request.UpdateNoteRequest;
import ch.niklas409.riftvision.dto.response.NoteResponse;
import ch.niklas409.riftvision.exception.*;
import ch.niklas409.riftvision.repository.CoachClientRelationRepository;
import ch.niklas409.riftvision.repository.MatchRepository;
import ch.niklas409.riftvision.repository.NoteRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
            throw new InvalidCoachRoleException("User does not have coach role");
        }
        UserEntity student = userRepository.findById(request.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if (student.getRole() != Role.USER) {
            throw new InvalidStudentRoleException("User does not have student role");
        }
        if (coachClientRelationRepository.findByCoachAndStudent(coach, student).isEmpty()) {
            throw new CoachStudentRelationNotFoundException("No relationship was found between the student and the coach.");
        }
        if(!matchRepository.existsByMatchId(matchId)) {
            throw new ResourceNotFoundException("No Match with Id " + matchId + " found");
        }
        NoteEntity note = new NoteEntity(matchId, coach, student, request.getContent(), Instant.now());
        NoteEntity savedNote = noteRepository.save(note);
        return new NoteResponse(savedNote.getId(), savedNote.getMatchId(), savedNote.getContent(), savedNote.getCoach().getEmail(), savedNote.getStudent().getEmail(), savedNote.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getNotesForMatch(Long studentId, String matchId, String coachEmail) {
        List<NoteResponse> noteResponses = new ArrayList<>();
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("User does not have coach role");
        }
        UserEntity student = userRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if (student.getRole() != Role.USER) {
            throw new InvalidStudentRoleException("User does not have student role");
        }
        if (coachClientRelationRepository.findByCoachAndStudent(coach, student).isEmpty()) {
            throw new CoachStudentRelationNotFoundException("No relationship was found between the student and the coach.");
        }
        if(!matchRepository.existsByMatchId(matchId)) {
            throw new ResourceNotFoundException("No Match with Id " + matchId + " found");
        }
        for(NoteEntity note : noteRepository.findByCoachAndStudentAndMatchId(coach, student, matchId)) {
            noteResponses.add(new NoteResponse(note.getId(),
                    note.getMatchId(),
                    note.getContent(),
                    note.getCoach().getEmail(),
                    note.getStudent().getEmail(),
                    note.getCreatedAt()));
        }
        return noteResponses;
    }

    @Transactional
    public NoteResponse updateNote(Long noteId, String coachEmail, UpdateNoteRequest request) {
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("User does not have coach role");
        }
        NoteEntity note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        if (!note.getCoach().getId().equals(coach.getId())) {
            throw new UnauthorizedCoachAccessException("Coach is not allowed to modify this note");
        }
        note.setContent(request.getContent());
        NoteEntity savedNote = noteRepository.save(note);
        return new NoteResponse(savedNote.getId(), savedNote.getMatchId(), savedNote.getContent(), savedNote.getCoach().getEmail(), savedNote.getStudent().getEmail(), savedNote.getCreatedAt());
    }

    @Transactional
    public void deleteNote(Long noteId, String coachEmail) {
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("User does not have coach role");
        }
        NoteEntity note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        if (!note.getCoach().getId().equals(coach.getId())) {
            throw new UnauthorizedCoachAccessException("Coach is not allowed to modify this note");
        }
        noteRepository.delete(note);
    }

}
