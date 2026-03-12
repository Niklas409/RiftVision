package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.request.CreateNoteRequest;
import ch.niklas409.riftvision.dto.request.CreateTaskRequest;
import ch.niklas409.riftvision.dto.request.UpdateNoteRequest;
import ch.niklas409.riftvision.dto.request.UpdateTaskRequest;
import ch.niklas409.riftvision.dto.response.CoachStudentResponse;
import ch.niklas409.riftvision.dto.response.NoteResponse;
import ch.niklas409.riftvision.dto.response.TaskResponse;
import ch.niklas409.riftvision.service.CoachClientRelationService;
import ch.niklas409.riftvision.service.NoteService;
import ch.niklas409.riftvision.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachClientRelationController {

    private final CoachClientRelationService coachClientRelationService;
    private final NoteService noteService;
    private final TaskService taskService;

    public CoachClientRelationController(CoachClientRelationService coachClientRelationService, NoteService noteService, TaskService taskService) {
        this.coachClientRelationService = coachClientRelationService;
        this.noteService = noteService;
        this.taskService = taskService;
    }

    @PostMapping("/students/{studentId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<Void> assignStudentToCoach(@PathVariable Long studentId, Authentication authentication) {
        coachClientRelationService.assignCoachToStudent(authentication.getName(), studentId);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/students/{studentId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<Void> removeStudentFromCoach(@PathVariable Long studentId, Authentication authentication) {
        coachClientRelationService.removeStudentFromCoach(authentication.getName(), studentId);
        return ApiResponse.success(null);
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<List<CoachStudentResponse>> getStudentsForCoach(Authentication authentication) {
        return ApiResponse.success(coachClientRelationService.getStudentsForCoach(authentication.getName()));
    }

    @PostMapping("/matches/{matchId}/notes")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<NoteResponse> createNote(@PathVariable String matchId, @Valid @RequestBody CreateNoteRequest request, Authentication authentication) {
        return ApiResponse.success(noteService.createNote(matchId, authentication.getName(), request));
    }

    @GetMapping("/matches/{matchId}/notes")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<List<NoteResponse>> getNotes(@PathVariable String matchId, @RequestParam Long studentId, Authentication authentication) {
        return ApiResponse.success(noteService.getNotesForMatch(studentId, matchId, authentication.getName()));
    }

    @PutMapping("/notes/{noteId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<NoteResponse> updateNote(@PathVariable Long noteId, @Valid @RequestBody UpdateNoteRequest request, Authentication authentication) {
        return ApiResponse.success(noteService.updateNote(noteId, authentication.getName(), request));
    }

    @DeleteMapping("/notes/{noteId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<Void> deleteNote(@PathVariable Long noteId, Authentication authentication) {
        noteService.deleteNote(noteId, authentication.getName());
        return ApiResponse.success(null);
    }

    @PostMapping("/students/{studentId}/tasks")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<TaskResponse> createTask(@PathVariable Long studentId, @Valid @RequestBody CreateTaskRequest request, Authentication authentication) {
        return ApiResponse.success(taskService.createTask(studentId, authentication.getName(), request));
    }

    @GetMapping("/students/{studentId}/tasks")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<List<TaskResponse>> getTasks(@PathVariable Long studentId, Authentication authentication) {
        return ApiResponse.success(taskService.getTasksForStudent(studentId, authentication.getName()));
    }

    @DeleteMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<Void> deleteTask(@PathVariable Long taskId, Authentication authentication) {
        taskService.deleteTask(taskId, authentication.getName());
        return ApiResponse.success(null);
    }

    @PutMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('COACH') or hasRole('ADMIN')")
    public ApiResponse<TaskResponse> updateTask(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskRequest request, Authentication authentication) {
        return ApiResponse.success(taskService.updateTask(taskId, authentication.getName(), request));
    }

}
