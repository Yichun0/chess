package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage{
    private String message;
    public ErrorMessage(String errorMessage){
        super(ServerMessageType.ERROR);
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
