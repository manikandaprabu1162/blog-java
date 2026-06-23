package company.com.blog.dto;

public class AuthResponse {
    private String message;
    private String username;
    private String role;
    private boolean success;

    public AuthResponse(String message, String username, String role, boolean success) {
        this.message = message;
        this.username = username;
        this.role = role;
        this.success = success;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}