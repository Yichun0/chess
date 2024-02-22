package dataAccess.Model;

public class AuthToken {
    // constructor
    String username;
    String authToken;
    public AuthToken(String username, String authToken){
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken(){
        return authToken;
    }

}
