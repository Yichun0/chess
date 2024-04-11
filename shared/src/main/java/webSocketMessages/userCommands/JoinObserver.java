package webSocketMessages.userCommands;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserver extends UserGameCommand {
    private int gameID;

    public int getGameID() {
        return gameID;
    }

    public JoinObserver(int gameID, String authToken){
        super(authToken);
        this.commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameID;
    }
}
