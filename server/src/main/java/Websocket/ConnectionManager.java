package Websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.SQLGameDAO;
import exception.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;

import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Vector<Connection>> connections = new ConcurrentHashMap<>();

    public void add(int gameID, Session session, String authToken) {
        // record a connection when user joined a game
        // find gameID
        // if game doesn't exist, create a new vector of connection
        var connection = new Connection(authToken, session);
        if (connections.containsKey(gameID)) {
            // game already documented
            Vector<Connection> individualGame = connections.get(gameID);
            individualGame.add(connection);
            connections.put(gameID, individualGame);
        } else {
            Vector<Connection> individualGame = new Vector<>();
            individualGame.add(connection);
            connections.put(gameID, individualGame);
        }
    }
    public void deleteUser(int gameID, String authToken){
        Vector<Connection> individualGame = connections.get(gameID);
        // getting the game that the user is in
        for (Connection user: individualGame){
            if(Objects.equals(user.getAuthToken(), authToken)){
                individualGame.remove(user);
                return;
            }
        }
    }

    public void deleteGame(int gameID,String authToken) {
        Vector<Connection> individualGame = connections.get(gameID);
        for (Connection user : individualGame){
            if (!Objects.equals(user.getAuthToken(), authToken)){
                individualGame.remove(user);
            }
        }
    }
    public void sentErrorMessage(String message,String authToken) throws IOException {
        ErrorMessage errorMessage = new ErrorMessage(message);
//        Vector<Connection> individualGame = connections.get(gameID);
        for (int gameID : connections.keySet()){
            for (var connection : connections.get(gameID)){
                if(connection.session.isOpen()){
                    if(connection.getAuthToken().equalsIgnoreCase(authToken)){
                        String errorMsg = new Gson().toJson(errorMessage);
                        connection.sendMessage(errorMsg);
                    }
                }
            }
        }
    }
    public void notifyEveryUser(int gameID, String message) throws IOException {
        Vector<Connection> individualGame = connections.get(gameID);
        // getting the game that the user is in
        for (Connection user: individualGame) {
            if (user.session.isOpen()) {
                Notification notification = new Notification(message);
                user.sendMessage(new Gson().toJson(notification));
            }
        }
    }


    public void notify(int gameID, String authToken,String message) throws IOException {
        Vector<Connection> individualGame = connections.get(gameID);
        // broadcasting to everyone else
        for (Connection user : individualGame) {
            if (user.session.isOpen()) {
                if (!Objects.equals(user.getAuthToken(), authToken)) {
                    Notification notification = new Notification(message);
                    user.sendMessage(new Gson().toJson(notification));
                }
            }
        }
    }

    public void selfLoadGame(String playerColor, int gameID, Session session) throws DataAccessException, IOException {
        GameDAO gameDAO = new SQLGameDAO();
        ChessGame currentGame = gameDAO.getGame(gameID).getGame();
        LoadGame loadGame = new LoadGame(currentGame,playerColor);
        session.getRemote().sendString(new Gson().toJson(loadGame, LoadGame.class));
    }

    public void allLoadGame(String playColor,int gameID, ChessGame updatedGame) throws IOException {
        Vector<Connection> individualGame = connections.get(gameID);
        // getting the game that the user is in
        for (Connection user: individualGame) {
            if (user.session.isOpen()) {
                LoadGame loadGame = new LoadGame(updatedGame, playColor);
                user.sendMessage(new Gson().toJson(loadGame));
            }
        }
    }
}
