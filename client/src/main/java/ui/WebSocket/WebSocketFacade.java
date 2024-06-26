package ui.WebSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import exception.ResponseException;
import ui.CreateBoard;
import ui.GamePlay;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {
    private final Session session;
    static WebSocketFacade wsf;

    static {
        try {
            wsf = new WebSocketFacade();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    public WebSocketFacade() throws ResponseException {
        try {
            URI uri = new URI("ws://localhost:8080/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()){
                        case ERROR -> {
                            ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);
                            System.out.println(errorMessage.getMessage());
                        }
                        case NOTIFICATION -> {
                            Notification notification = new Gson().fromJson(message, Notification.class);
                            System.out.println(notification.getMessage());
                        }
                        case LOAD_GAME -> {
                            LoadGame gameObj = new Gson().fromJson(message, LoadGame.class);
                            ChessGame currentGame = gameObj.getGame();
                            String color = gameObj.getPalyerColor();
                            CreateBoard.drawGeneralBoard(currentGame.getBoard(),color);
                            GamePlay.board = currentGame.getBoard();

                        }

                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }
    // methods for each userGameCommands to send message to server
    public void joinPlayer(String authToken, int gameID, ChessGame.TeamColor playerColor){
        try {
            var command = new JoinPlayer(gameID,playerColor,authToken);
            this.sendMessage(new Gson().toJson(command));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void joinObserver(int gameID, String authToken){
        try {
            var command = new JoinObserver(gameID,authToken);
            this.sendMessage(new Gson().toJson(command));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeMove(String authToken, int gameID, ChessMove move){
        try{
            var command = new MakeMove(authToken,gameID,move);
            this.sendMessage(new Gson().toJson(command));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
    public void leave(String authToken, int gameID){
        try{
            var command = new Leave(authToken,gameID);
            this.sendMessage(new Gson().toJson(command));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void resign(String authToken, int gameID){
        try{
            var command = new Resign(authToken,gameID);
            this.sendMessage(new Gson().toJson(command));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}


