package ch.niklas409.riftvision.dto.response;

public class CoachStudentResponse {

    private long id;
    private String email;
    private String role;

    public CoachStudentResponse(long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
