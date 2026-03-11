package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.CoachClientRelationEntity;
import ch.niklas409.riftvision.domain.entity.Role;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.response.CoachStudentResponse;
import ch.niklas409.riftvision.exception.*;
import ch.niklas409.riftvision.repository.CoachClientRelationRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoachClientRelationService {

    private final CoachClientRelationRepository coachClientRelationRepository;
    private final UserRepository userRepository;

    public CoachClientRelationService(CoachClientRelationRepository coachClientRelationRepository, UserRepository userRepository) {
        this.coachClientRelationRepository = coachClientRelationRepository;
        this.userRepository = userRepository;
    }

    public void assignCoachToStudent(String coachEmail, Long studentId) {
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("Coach hasn't Coach Role");
        }
        UserEntity student = userRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if(student.getRole().equals(Role.COACH) || student.getRole().equals(Role.ADMIN)) {
            throw new InvalidCoachRoleException("Student hasn't User Role");
        }
        if(coach.equals(student)) {
            throw new SelfCoachingNotAllowedException("Coach cannot assign himself as student");
        }
        if(coachClientRelationRepository.existsByCoachAndStudent(coach, student)) {
            throw new RelationAlreadyExistsException("Relation already exists");
        }
        coachClientRelationRepository.save(new CoachClientRelationEntity(coach, student, Instant.now()));
    }

    @Transactional(readOnly = true)
    public List<CoachStudentResponse> getStudentsForCoach(String coachEmail) {
        List<CoachStudentResponse> students = new ArrayList<>();
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        List<CoachClientRelationEntity> coachRelations = coachClientRelationRepository.findByCoach(coach);
        for(CoachClientRelationEntity relationEntity : coachRelations) {
            UserEntity student = relationEntity.getStudent();
            students.add(new CoachStudentResponse(student.getId(), student.getEmail(), student.getRole().name()));
        }
        return students;
    }

    public void removeStudentFromCoach(String coachEmail, Long studentId) {
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("Coach hasn't Coach Role");
        }
        UserEntity student = userRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Optional<CoachClientRelationEntity> relationEntity = coachClientRelationRepository.findByCoachAndStudent(coach, student);
        if(relationEntity.isEmpty()) {
            throw new CoachStudentRelationNotFoundException("No relationship was found between the student and the coach.");
        }
        coachClientRelationRepository.delete(relationEntity.get());
    }

}
