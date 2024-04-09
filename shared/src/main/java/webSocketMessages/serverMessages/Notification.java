package webSocketMessages.serverMessages;

import java.lang.reflect.Type;

public class Notification extends ServerMessage{
    private String message;
    public Notification(String message){
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
