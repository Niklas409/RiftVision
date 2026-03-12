package ch.niklas409.riftvision.service;

import ch.niklas409.riftvision.domain.entity.NoteEntity;
import ch.niklas409.riftvision.domain.entity.Role;
import ch.niklas409.riftvision.domain.entity.TaskEntity;
import ch.niklas409.riftvision.domain.entity.UserEntity;
import ch.niklas409.riftvision.dto.request.CreateTaskRequest;
import ch.niklas409.riftvision.dto.request.UpdateTaskRequest;
import ch.niklas409.riftvision.dto.response.NoteResponse;
import ch.niklas409.riftvision.dto.response.TaskResponse;
import ch.niklas409.riftvision.exception.*;
import ch.niklas409.riftvision.repository.CoachClientRelationRepository;
import ch.niklas409.riftvision.repository.TaskRepository;
import ch.niklas409.riftvision.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final CoachClientRelationRepository coachClientRelationRepository;
    private final TaskRepository taskRepository;

    public TaskService(UserRepository userRepository, CoachClientRelationRepository coachClientRelationRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.coachClientRelationRepository = coachClientRelationRepository;
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(Long studentId, String coachEmail, CreateTaskRequest request) {
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
        TaskEntity taskEntity = taskRepository.save(new TaskEntity(coach, student, request.getDescription(), false, Instant.now()));
        return new TaskResponse(taskEntity.getId(),
                taskEntity.getDescription(),
                taskEntity.isCompleted(),
                taskEntity.getCoach().getEmail(),
                taskEntity.getStudent().getEmail(),
                taskEntity.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksForStudent(Long studentId, String coachEmail) {
        List<TaskResponse> taskResponses = new ArrayList<>();
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
        for(TaskEntity task : taskRepository.findByCoachAndStudent(coach, student)) {
            taskResponses.add(new TaskResponse(task.getId(),
                    task.getDescription(),
                    task.isCompleted(),
                    task.getCoach().getEmail(),
                    task.getStudent().getEmail(),
                    task.getCreatedAt()));
        }
        return taskResponses;
    }

    @Transactional
    public TaskResponse completeTask(Long taskId, String studentEmail) {
        UserEntity student = userRepository.findByEmail(studentEmail).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if (student.getRole() != Role.USER) {
            throw new InvalidStudentRoleException("User does not have student role");
        }
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if(!task.getStudent().getId().equals(student.getId())) {
            throw new UnauthorizedStudentAccessException("Student is not allowed to complete this task");
        }
        if(task.isCompleted()) {
            throw new TaskAlreadyCompletedException("Task has already been completed");
        }
        task.setCompleted(true);
        taskRepository.save(task);
        return new TaskResponse(task.getId(),
                task.getDescription(),
                task.isCompleted(),
                task.getCoach().getEmail(),
                task.getStudent().getEmail(),
                task.getCreatedAt());
    }

    @Transactional
    public TaskResponse uncompleteTask(Long taskId, String studentEmail) {
        UserEntity student = userRepository.findByEmail(studentEmail).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if (student.getRole() != Role.USER) {
            throw new InvalidStudentRoleException("User does not have student role");
        }
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if(!task.getStudent().getId().equals(student.getId())) {
            throw new UnauthorizedStudentAccessException("Student is not allowed to uncomplete this task");
        }
        if(!task.isCompleted()) {
            throw new TaskAlreadyOpenException("Task is already open");
        }
        task.setCompleted(false);
        taskRepository.save(task);
        return new TaskResponse(task.getId(),
                task.getDescription(),
                task.isCompleted(),
                task.getCoach().getEmail(),
                task.getStudent().getEmail(),
                task.getCreatedAt());
    }

    @Transactional
    public void deleteTask(Long taskId, String coachEmail) {
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("User does not have coach role");
        }
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (!task.getCoach().getId().equals(coach.getId())) {
            throw new UnauthorizedCoachAccessException("Coach is not allowed to delete this task");
        }
        taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, String coachEmail, UpdateTaskRequest request) {
        UserEntity coach = userRepository.findByEmail(coachEmail).orElseThrow(() -> new ResourceNotFoundException("Coach not found"));
        if (!(coach.getRole().equals(Role.COACH) || coach.getRole().equals(Role.ADMIN))) {
            throw new InvalidCoachRoleException("User does not have coach role");
        }
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (!task.getCoach().getId().equals(coach.getId())) {
            throw new UnauthorizedCoachAccessException("Coach is not allowed to update this task");
        }
        task.setDescription(request.getDescription());
        taskRepository.save(task);
        return new TaskResponse(task.getId(),
                task.getDescription(),
                task.isCompleted(),
                task.getCoach().getEmail(),
                task.getStudent().getEmail(),
                task.getCreatedAt());
    }

}
