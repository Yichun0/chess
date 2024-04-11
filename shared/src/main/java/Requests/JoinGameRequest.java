package Requests;

public class JoinGameRequest {
    private String playerColor;
    private Integer gameID;

    public String getPlayerColor() {
        return playerColor;
    }

    public Integer getGameID() {
        return gameID;
    }

    public JoinGameRequest(String playerColor, int gameID){
        this.playerColor = playerColor;
        this.gameID = gameID;
    }
}
