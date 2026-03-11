package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.request.CreateNoteRequest;
import ch.niklas409.riftvision.dto.response.CoachStudentResponse;
import ch.niklas409.riftvision.dto.response.NoteResponse;
import ch.niklas409.riftvision.service.CoachClientRelationService;
import ch.niklas409.riftvision.service.NoteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachClientRelationController {

    private final CoachClientRelationService coachClientRelationService;
    private final NoteService noteService;

    public CoachClientRelationController(CoachClientRelationService coachClientRelationService, NoteService noteService) {
        this.coachClientRelationService = coachClientRelationService;
        this.noteService = noteService;
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
    public ApiResponse<NoteResponse> createNote(@PathVariable String matchId, @RequestBody CreateNoteRequest request, Authentication authentication) {
        return ApiResponse.success(noteService.createNote(matchId, authentication.getName(), request));
    }

}
