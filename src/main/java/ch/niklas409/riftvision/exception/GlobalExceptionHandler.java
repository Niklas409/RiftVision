package ch.niklas409.riftvision.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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

}
