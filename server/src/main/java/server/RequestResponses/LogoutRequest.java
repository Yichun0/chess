package server.RequestResponses;

public class LogoutRequest {
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private String authToken;
    public LogoutRequest(String authToken){
        this.authToken = authToken;
    }
}