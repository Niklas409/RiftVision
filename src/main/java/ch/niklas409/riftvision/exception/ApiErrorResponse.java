package ch.niklas409.riftvision.exception;

import java.util.List;

public class ApiErrorResponse {

    private int status;
    private String message;
    private List<FieldErrorResponse> errors;
    private String path;

    public ApiErrorResponse(int status, String message, List<FieldErrorResponse> errors, String path) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorResponse> getErrors() {
        return errors;
    }

    public String getPath() {
        return path;
    }
}
