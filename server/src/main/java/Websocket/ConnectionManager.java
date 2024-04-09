package Websocket;

import Model.AuthData;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.SQLGameDAO;
import exception.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;

import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Vector<Connection>> connections = new ConcurrentHashMap<>();

    public void add(int gameID, Session session, String username) {
        // record a connection when user joined a game
        // find gameID
        // if game doesn't exist, create a new vector of connection
        var connection = new Connection(username, session);
        if (!isNull(gameID)) {
            if (connections.contains(gameID)) {
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
    }

    public void serverMessage(int gameID, Session session, String rootUser, String message) throws IOException {
        Vector<Connection> individualGame = connections.get(gameID);
        // broadcasting to everyone else
        for (Connection user : individualGame) {
            if (user.session.isOpen()) {
                if (!Objects.equals(user.getUsername(), rootUser)) {
                    Notification notification = new Notification(message);
                    user.sendMessage(new Gson().toJson(notification, Notification.class));
                }
            }
        }
    }
    public void loadGame(String playerColor, int gameID, Session session) throws DataAccessException, IOException {
        GameDAO gameDAO = new SQLGameDAO();
        String currentGame = gameDAO.getGame(gameID);
        ChessGame game = new Gson().fromJson(currentGame, ChessGame.class);
        LoadGame loadGame = new LoadGame(game,playerColor);
        session.getRemote().sendString(new Gson().toJson(loadGame, LoadGame.class));
    }
}
