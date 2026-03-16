package ch.niklas409.riftvision.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();
        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.add(new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors, request.getRequestURI());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGeneric(Exception exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error", List.of(), request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleInvalidCredentials(InvalidCredentialsException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(RelationAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleRelationAlreadyExistsException(RelationAlreadyExistsException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(InvalidCoachRoleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleInvalidCoachRoleException(InvalidCoachRoleException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(InvalidStudentRoleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleInvalidStudentRoleException(InvalidStudentRoleException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(SelfCoachingNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleSelfCoachingNotAllowedException(SelfCoachingNotAllowedException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(CoachStudentRelationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleCoachStudentRelationNotFoundException(CoachStudentRelationNotFoundException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(UnauthorizedCoachAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleUnauthorizedCoachAccessException(UnauthorizedCoachAccessException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(UnauthorizedStudentAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleUnauthorizedStudentAccessException(UnauthorizedStudentAccessException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(TaskAlreadyCompletedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleTaskAlreadyCompletedException(TaskAlreadyCompletedException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(TaskAlreadyOpenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleTaskAlreadyOpenException(TaskAlreadyOpenException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

    @ExceptionHandler(PlayerAlreadyAssignedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handlePlayerAlreadyAssignedException(PlayerAlreadyAssignedException exception, HttpServletRequest request) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage(), List.of(), request.getRequestURI());
    }

}
