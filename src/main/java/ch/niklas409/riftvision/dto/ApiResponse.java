package ch.niklas409.riftvision.dto;

public class ApiResponse<T> {

    private final String message;
    private final T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Success", data);
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
