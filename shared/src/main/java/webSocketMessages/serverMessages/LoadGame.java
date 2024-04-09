package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage{
    ChessGame game;
    String palyerColor;
    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public String getPalyerColor() {
        return palyerColor;
    }

    public void setPalyerColor(String palyerColor) {
        this.palyerColor = palyerColor;
    }

    public LoadGame(ChessGame game, String playerColor) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
        this.palyerColor = playerColor;
    }
}
