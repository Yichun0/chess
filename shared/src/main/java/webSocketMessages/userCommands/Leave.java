package webSocketMessages.userCommands;

public class Leave extends UserGameCommand {
    private int gameID;

    public int getGameID() {
        return gameID;
    }

    public Leave(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }
}
