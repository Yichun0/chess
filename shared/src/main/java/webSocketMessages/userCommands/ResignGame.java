package webSocketMessages.userCommands;

import Model.GameData;
import webSocketMessages.userCommands.UserGameCommand;

public class ResignGame extends UserGameCommand {
    int GameID;

    public int getGameID() {
        return GameID;
    }

    public ResignGame(String authToken, int gameID) {
        super(authToken);
        this.GameID = gameID;
    }
}
