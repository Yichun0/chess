package Websocket;

import Model.AuthData;
import com.google.gson.Gson;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.SQLAuthDao;
import dataAccess.DAO.SQLGameDAO;
import exception.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws DataAccessException {
        UserGameCommand userCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userCommand.getCommandType()){
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver();
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }

    }
    public void joinPlayer(Session session, String message) throws DataAccessException {
        // message is information for joinplayer
        JoinPlayer joinPlayer = new Gson().fromJson(message, JoinPlayer.class);
        int gameID = joinPlayer.getGameID();
        String authToken = joinPlayer.getAuthString();
        AuthDAO authDAO = new SQLAuthDao();
        String username = authDAO.getUsername(new AuthData(null,authToken));
        GameDAO gameDAO = new SQLGameDAO();
        // find and verify the game
        // verify authtoken, username
    }

    public void joinObserver(){

    }

    public void makeMove(){

    }
    public void leave(){

    }
    public void resign(){

    }
}
