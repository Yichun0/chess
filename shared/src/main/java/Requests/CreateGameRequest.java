package Requests;

public class CreateGameRequest {
    private String gameName;

    public String getGameName() {
        return gameName;
    }

    public CreateGameRequest(String gameName){
        this.gameName = gameName;
    }
}
