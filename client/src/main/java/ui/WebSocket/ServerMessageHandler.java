package ui.WebSocket;

import webSocketMessages.serverMessages.ServerMessage;

import javax.management.Notification;

public interface ServerMessageHandler {
    void notify(ServerMessage serverMessage);
}
