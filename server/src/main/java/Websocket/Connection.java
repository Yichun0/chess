package Websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    // each individual user that's connecting to the game
    public String authToken;
    public Session session;

    public String getAuthToken() {
        return authToken;
    }

    public Connection(String authToken, Session session){
        this.authToken = authToken;
        this.session = session;
    }
    public void sendMessage(String message) throws IOException{
        session.getRemote().sendString(message);
    }
}
