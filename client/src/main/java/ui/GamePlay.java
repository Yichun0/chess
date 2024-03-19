package ui;

import java.util.Scanner;

public class GamePlay {
    private String serverUrl;
    private Scanner scanner;
    private ServerFacade serverFacade;

    public GamePlay(Scanner scanner, ServerFacade server) {
        this.scanner = new Scanner(System.in);
        this.serverFacade = server;
    }
}
