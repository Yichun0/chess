package Response;

public class LoginRespond{
    public String username;
    public String authToken;

    public String getUsername() {
        return username;
    }
    public String getAuthToken() {
        return authToken;
    }

    public LoginRespond(String username, String authToken){
        this.authToken = authToken;
        this.username = username;
    }
}
