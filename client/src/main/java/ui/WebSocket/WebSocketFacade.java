package ui.WebSocket;

import com.google.gson.Gson;
import com.sun.nio.sctp.NotificationHandler;
import exception.DataAccessException;
import exception.ResponseException;
import webSocketMessages.JoinPlayer;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.management.Notification;
import javax.websocket.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class WebSocketFacade extends Endpoint {

    ServerMessageHandler serverMessageHandler;
    private final Session session;

    public WebSocketFacade(String url, ServerMessageHandler serverMessageHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.serverMessageHandler = serverMessageHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    serverMessageHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }
    // methods for each userGameCommands to send message to server
    public void joinPlayer(String Message){

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


