package Response;

public class LoginRespond{
    public String username;
    public String authToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LoginRespond(String username, String authToken){
        this.authToken = authToken;
        this.username = username;
    }
}
