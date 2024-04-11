package webSocketMessages.userCommands;

import Model.GameData;
import webSocketMessages.userCommands.UserGameCommand;

public class ResignGame extends UserGameCommand {
    int gameID;

    public int getGameID() {
        return this.gameID;
    }

    public ResignGame(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
    }
}
