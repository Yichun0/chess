package server.Response;

public class CreateGameRespond {
    int gameID;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public CreateGameRespond(int gameID){
        this.gameID = gameID;
    }
}
