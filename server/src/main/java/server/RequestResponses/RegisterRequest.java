package server.RequestResponses;

public class RegisterRequest {
    private String username;
    private String password;
    private String email;


    public RegisterRequest(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String setUsername(String username) {
        return username;
    }

    public String setPassword(String password) {
        return password;
    }

    public String setEmail(String email) {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
