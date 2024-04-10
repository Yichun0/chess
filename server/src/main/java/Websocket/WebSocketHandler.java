package Websocket;

import Model.AuthData;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.SQLAuthDao;
import dataAccess.DAO.SQLGameDAO;
import exception.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.sql.SQLException;

@WebSocket
public class WebSocketHandler {

//    Map<Integer, List<Connection>> individualGameMap = new HashMap<>();
    private Session session;
    private ConnectionManager connectionManager = new ConnectionManager();

    //    public final ConcurrentHashMap<String, Connection> userMap = new ConcurrentHashMap<>();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws DataAccessException, SQLException, IOException {
        UserGameCommand userCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userCommand.getCommandType()){
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }

    }
    public void joinPlayer(Session session, String message) throws DataAccessException, IOException {
        // message is information for joinplayer
        JoinPlayer joinPlayer = new Gson().fromJson(message, JoinPlayer.class);
        int gameID = joinPlayer.getGameID();
        String authToken = joinPlayer.getAuthString();
        AuthDAO authDAO = new SQLAuthDao();
        String username = authDAO.getUsername(new AuthData(null,authToken));
        ChessGame.TeamColor playerColor =  joinPlayer.getPlayerColor();
        GameDAO gameDAO = new SQLGameDAO();
//      username match the white username
        if (gameDAO.playerTaken(gameID,playerColor.toString(),username)){
            String errorMessage = playerColor.toString() + " user is already taken";
            connectionManager.sentErrorMessage(errorMessage,session,username);
        } else if (!gameDAO.findGame(gameID)) {
           // check bad gameID
            String error = "Bad gameID";
            connectionManager.sentErrorMessage(error, session,username);
        } else if (!authDAO.findAuthToken(new AuthData(null,authToken))) {
            String error = "Bad authToken";
            connectionManager.sentErrorMessage(error,session,username);
        }
        else{
            connectionManager.add(gameID,session,username);
            String serMessage = "\n\033[0mNotification:  " + username + " has joined game " + gameID + " as " + playerColor.toString();
            connectionManager.notify(gameID,username,serMessage);
            connectionManager.loadGame(playerColor.toString(),gameID,session);
        }
    }

    public void joinObserver(Session session, String message) throws DataAccessException, IOException {
        JoinObserver joinObserver = new Gson().fromJson(message, JoinObserver.class);
        int gameID = joinObserver.getGameID();
        String authToken = joinObserver.getAuthString();
        AuthDAO authDAO = new SQLAuthDao();
        String username = authDAO.getUsername(new AuthData(null, authToken));
        GameDAO gameDAO = new SQLGameDAO();
        if (!gameDAO.findGame(gameID)) {
            // check bad gameID
            String error = "Bad gameID";
            connectionManager.sentErrorMessage(error, session, username);
        } else if (!authDAO.findAuthToken(new AuthData(null, authToken))) {
            String error = "Bad authToken";
            connectionManager.sentErrorMessage(error, session, username);
        } else {
            connectionManager.add(gameID, session, username);
            connectionManager.loadGame("white", gameID, session);
            String serMessage = "\n\033[0mNotification:  " + username + " has joined game " + gameID + " as observer";
            connectionManager.notify(gameID, username, serMessage);
        }
    }

    public void makeMove(){

    }
    public void leave(){
        // delete vector of the session


    }
    public void resign(){

    }

}
