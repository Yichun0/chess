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
//        String resp = "hi, this is the server";
//        System.out.println(resp);
//        session.getRemote().sendString("hello");
        UserGameCommand userCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userCommand.getCommandType()){
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver();
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }

    }
    public void joinPlayer(Session session, String message) throws DataAccessException, SQLException, IOException {
        // message is information for joinplayer
        JoinPlayer joinPlayer = new Gson().fromJson(message, JoinPlayer.class);
        int gameID = joinPlayer.getGameID();
        String authToken = joinPlayer.getAuthString();
        AuthDAO authDAO = new SQLAuthDao();
        String username = authDAO.getUsername(new AuthData(null,authToken));
        ChessGame.TeamColor playerColor =  joinPlayer.getPlayerColor();
        connectionManager.add(gameID,session,username);
        String serMessage = "\n\033[0mNotification:  " + username + " has joined game " + gameID + " as " + playerColor.toString();
        connectionManager.serverMessage(gameID,username,serMessage);
        connectionManager.loadGame(playerColor.toString(),gameID,session);


//        userMap.put(authToken,connection);
//        // find and verify the game
//        if (gameID != gameDAO.getGameID(gameName)){
//            badGameID(session);
//        } // verify authtoken, username
//        else if (authDAO.findAuthToken(new AuthData(username,authToken))) {
//            badAuthToken(session);
//        }
        // verify player color not taken
    }

    public void joinObserver(){

    }

    public void makeMove(){

    }
    public void leave(){

    }
    public void resign(){

    }
//
//    public void badGameID(Session session) throws IOException {
//        String error = String.format("Invalid game ID");
//        ServerMessage serverMessage = new ServerMessage();
//        String errorMessage = serverMessage.serverErrorMessage(ServerMessage.ServerMessageType.ERROR,error);
//        session.getRemote().sendString(new Gson().toJson(errorMessage));
//    }
//    public void badAuthToken(Session session) throws IOException{
//        String error = String.format("Invalid authToken");
//        ServerMessage serverMessage = new ServerMessage();
//        String errorMessage = serverMessage.serverErrorMessage(ServerMessage.ServerMessageType.ERROR,error);
//        session.getRemote().sendString(new Gson().toJson(errorMessage));
//    }

}
