package Websocket;

import com.google.gson.Gson;
import exception.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    @OnWebSocketMessage
    public void onMessage(Session session, String message){
        UserGameCommand userCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userCommand.getCommandType()){
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver();
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }

    }
    public void joinPlayer(Session session, String message){
        // message is information for joinplayer
        JoinPlayer joinPlayer = new Gson().fromJson(message, JoinPlayer.class);
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
