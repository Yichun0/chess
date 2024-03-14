package Requests;

public class JoinGameRequest {
    private String playerColor;
    private Integer gameID;

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public JoinGameRequest(String playerColor, int gameID){
        this.playerColor = playerColor;
        this.gameID = gameID;
    }
}
