package Websocket;

import Model.AuthData;
import Model.GameData;
import chess.*;
import com.google.gson.Gson;
import dataAccess.DAO.*;
import exception.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.isNull;
import static spark.utils.Assert.notNull;

@WebSocket
public class WebSocketHandler {

//    Map<Integer, List<Connection>> individualGameMap = new HashMap<>();
    private ConnectionManager connectionManager = new ConnectionManager();
    private String username;
    private AuthDAO authDAO = new SQLAuthDao();
    private GameDAO gameDAO = new SQLGameDAO();

    //    public final ConcurrentHashMap<String, Connection> userMap = new ConcurrentHashMap<>();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws DataAccessException, SQLException, IOException {
        UserGameCommand userCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userCommand.getCommandType()){
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
            case MAKE_MOVE -> makeMove(session,message);
            case LEAVE -> leave(session,message);
            case RESIGN -> resign(session,message);
        }

    }
    public void joinPlayer(Session session, String message) throws DataAccessException, IOException, SQLException {
        // message is information for joinplayer
        JoinPlayer joinPlayer = new Gson().fromJson(message, JoinPlayer.class);
        int gameID = joinPlayer.getGameID();
        String authToken = joinPlayer.getAuthString();
        connectionManager.add(gameID,session,authToken);
        if(!authDAO.findAuthToken(authToken)){
            String error = "Bad authToken";
            username = authDAO.getUsername(new AuthData(null,authToken));
            connectionManager.sentErrorMessage(error,session);
        }
        username = authDAO.getUsername(new AuthData(null,authToken));
        ChessGame.TeamColor playerColor =  joinPlayer.getPlayerColor();
//      username match the white username
        if (gameDAO.playerTaken(gameID,playerColor.toString(),username)){
            String errorMessage = playerColor.toString() + " user is already taken";
            connectionManager.sentErrorMessage(errorMessage,session);
            return;
        } else if (!gameDAO.findGame(gameID)) {
           // check bad gameID
            String error = "Bad gameID";
            connectionManager.sentErrorMessage(error,session);
            return;
        }
        else{
            String serMessage = "\n\033[0mNotification:  " + username + " has joined game " + gameID + " as " + playerColor.toString();
            connectionManager.notify(gameID,authToken,serMessage);
            connectionManager.selfLoadGame(playerColor.toString(),gameID,session);
        }
    }

    public void joinObserver(Session session, String message) throws DataAccessException, IOException, SQLException {
        JoinObserver joinObserver = new Gson().fromJson(message, JoinObserver.class);
        int gameID = joinObserver.getGameID();
        String authToken = joinObserver.getAuthString();
        connectionManager.add(gameID,session,authToken);
        if(!authDAO.findAuthToken(authToken)){
            String error = "Bad authToken";
            connectionManager.sentErrorMessage(error,session);
            return;
        }
        username = authDAO.getUsername(new AuthData(null,authToken));
        if (!gameDAO.findGame(gameID)) {
            // check bad gameID
            String error = "Bad gameID";
            connectionManager.sentErrorMessage(error,session);
            return;
        } else {
            connectionManager.selfLoadGame("white", gameID, session);
            String serMessage = "\n\033[0mNotification:  " + username + " has joined game " + gameID + " as observer";
            connectionManager.notify(gameID, authToken, serMessage);
        }
    }

    public void makeMove(Session session, String message) throws DataAccessException, IOException, SQLException {
        MakeMoves makeMoves = new Gson().fromJson(message,MakeMoves.class);
        String authToken = makeMoves.getAuthString();
        ChessMove move = makeMoves.getMove();
        int gameID = makeMoves.getGameID();
        username = authDAO.getUsername(new AuthData(null,authToken));
        //authToken check
        if (!gameDAO.findGame(gameID)) {
            // check bad gameID
            String error = "Bad gameID";
            connectionManager.sentErrorMessage(error,session);
            return;
        }
        ChessPosition startPosition = move.getStartPosition();
        ChessGame chessGame = new ChessGame();
        Collection<ChessMove> validMoves = chessGame.validMoves(startPosition);
        if (!validMoves.contains(move)) {
            String errorMessage = "That's not a valid move";
            connectionManager.sentErrorMessage(errorMessage,session);
            return;
        }
        ChessGame.TeamColor playerColor = null;
        GameData game = gameDAO.getGame(gameID);
        ChessGame currentGame = game.getGame();
        if(game.getBlackUsername().equalsIgnoreCase(username)){
            playerColor = ChessGame.TeamColor.BLACK;
        } else if (game.getWhiteUsername().equalsIgnoreCase(username)) {
            playerColor = ChessGame.TeamColor.WHITE;
        }
        if (currentGame.isResigned()){
            String error = "The game is resigned.";
            connectionManager.sentErrorMessage(error,session);
            return;
        }
        // verify if we are moving the right piece
        ChessPiece piecemoved = currentGame.getBoard().getPiece(startPosition);
        if(playerColor != piecemoved.getTeamColor()){
            String errorMsg = "That's not your piece!";
            connectionManager.sentErrorMessage(errorMsg,session);
            return;
        }
        try {
            currentGame.makeMove(move);
        } catch (Exception e) {
            String error = e.getMessage();
            connectionManager.sentErrorMessage(error,session);
            return;
        }
        gameDAO.updateGame(currentGame,gameID);
        connectionManager.allLoadGame(playerColor.toString(), gameID,currentGame);
        String notification = username + " moved " + piecemoved + " from " + startPosition + " to " + move.getEndPosition();
        connectionManager.notify(gameID,authToken,notification);

    }
    public void leave(Session session, String message) throws DataAccessException, IOException, SQLException {
        // delete vector of the session
        Leave leaveGame = new Gson().fromJson(message, Leave.class);
        String authToken = leaveGame.getAuthString();
        int gameID = leaveGame.getGameID();
        String username = authDAO.getUsername(new AuthData(null,authToken));
        if (!gameDAO.findGame(gameID)) {
            // check bad gameID
            String error = "Bad gameID";
            connectionManager.sentErrorMessage(error,session);
        }
        else{
            connectionManager.deleteUser(gameID,authToken);
            GameData gameData = gameDAO.getGame(gameID);
            if (Objects.equals(gameData.getBlackUsername(), username)){
                gameDAO.deleteUser("black", gameID);

            } else {
                gameDAO.deleteUser("white",gameID);
            }
            String notification = username + " leaves the game.";
            connectionManager.notify(gameID,authToken,notification);
        }




    }
    public void resign(Session session, String message) throws DataAccessException, IOException, SQLException {
        Resign resignGameObj = new Gson().fromJson(message, Resign.class);
        int gameID = resignGameObj.getGameID();
        String authToken = resignGameObj.getAuthString();
        username = authDAO.getUsername(new AuthData(null,authToken));
        if (isNull(gameDAO.getGame(gameID))){
            String error = "The game is already resigned";
            connectionManager.sentErrorMessage(error,session);
            return;
        }
        GameData game = gameDAO.getGame(gameID);
        ChessGame currentGame = game.getGame();
        boolean x = currentGame.isResigned();
        ChessGame.TeamColor playerColor = null;;
        if(game.getBlackUsername().equalsIgnoreCase(username)){
            playerColor = ChessGame.TeamColor.BLACK;
        } else if (game.getWhiteUsername().equalsIgnoreCase(username)) {
            playerColor = ChessGame.TeamColor.WHITE;
        }
        if (!Objects.equals(game.getWhiteUsername(), username) && !Objects.equals(game.getBlackUsername(), username)){
            // user is observer, can't resign game
            String error = "You can't resign as observer";
            connectionManager.sentErrorMessage(error,session);
            return;
        }
        // no one can make move
        currentGame.setTeamTurn(null);
        currentGame.resignGame();
        gameDAO.updateGame(currentGame,gameID);
        gameDAO.deleteGameID(gameID);
        String notification = username + " resigned, the game is over.";
        connectionManager.notifyEveryUser(gameID, notification);
        //notification other users
        connectionManager.deleteGame(gameID);
        // remove the entire game from the hashmap
    }

}
