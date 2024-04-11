package webSocketMessages.userCommands;

import webSocketMessages.userCommands.UserGameCommand;

public class LeaveGame extends UserGameCommand {
    private int gameID;

    public int getGameID() {
        return gameID;
    }

    public LeaveGame(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }
}
