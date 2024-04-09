package webSocketMessages.userCommands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMoves extends UserGameCommand {
    private int gameID;
    private ChessMove move;

    public ChessMove getMove() {
        return move;
    }

    public int getGameID() {
        return gameID;
    }

    public MakeMoves(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
    }
}
