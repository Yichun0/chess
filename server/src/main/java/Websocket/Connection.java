package Websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    // each individual user that's connecting to the game
    public String username;
    public Session session;

    public String getUsername() {
        return username;
    }

    public Connection(String username, Session session){
        this.username = username;
        this.session = session;
    }
    public void sendMessage(String message) throws IOException{
        session.getRemote().sendString(message);
    }
}
