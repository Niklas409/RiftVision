package ch.niklas409.riftvision.controller;

import ch.niklas409.riftvision.dto.ApiResponse;
import ch.niklas409.riftvision.dto.response.TaskResponse;
import ch.niklas409.riftvision.service.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PatchMapping("/{taskId}/complete")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<TaskResponse> completeTask(@PathVariable Long taskId, Authentication authentication) {
        return ApiResponse.success(taskService.completeTask(taskId, authentication.getName()));
    }

    @PatchMapping("/{taskId}/uncomplete")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<TaskResponse> uncompleteTask(@PathVariable Long taskId, Authentication authentication) {
        return ApiResponse.success(taskService.uncompleteTask(taskId, authentication.getName()));
    }

}
