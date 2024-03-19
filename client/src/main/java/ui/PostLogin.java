package ui;

import Model.GameData;
import exception.DataAccessException;
import exception.ResponseException;

import java.util.Collection;
import java.util.Scanner;

public class PostLogin {
    private String serverUrl;
    private Scanner scanner;
    private ServerFacade serverFacade;
    public PostLogin(Scanner scanner, ServerFacade server){
        this.scanner = new Scanner(System.in);
        this.serverFacade = server;
    }
    public void run() throws ResponseException {
        System.out.println("Welcome to the chess game.");
        System.out.println("""
                create game
                list games
                join games
                observe games
                logout
                quit
                help
                """);
        String command = scanner.next();
        switch (command){
            case "help" -> help();
            case "logout" -> logout();
            case "create game" -> createGame();
            case "list games" -> listGames();
            case "join games" -> joinGame();
            case "observe games" -> observeGame();
            case "quit" -> quit();
            default -> run();
        }

    }
    public void help() throws ResponseException {
        // display text informing user actions
        System.out.println("Choose on of the options to start:");
        run();
    }

    public void logout() throws ResponseException {
    try{
        serverFacade.logout();
        System.out.println("successfully logged out");
        PreLogin preLogin = new PreLogin(scanner, serverFacade);
        preLogin.run();
    } catch (ResponseException e){
        System.out.println(e.getMessage());
    }
    }
    public void createGame() throws ResponseException{
        try {
            System.out.println("New game Name: ");
            String gameName = scanner.next();
            serverFacade.createGame(gameName);
            System.out.println("successfully create game" + gameName);
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
            help();
        }
    }
    public void listGames() throws ResponseException {
        int gameIndex = 1;
        System.out.println("Games: ");
        Collection<GameData> gamelist = serverFacade.listGames();
        for (int i = 0; i < gamelist.size(); i++) {
//            System.out.println(gameIndex + ". " + gamelist[i]);
        }
    }
    public void joinGame() throws ResponseException {
        Collection<GameData> games = serverFacade.listGames();
        System.out.println("game ID: ");
        int gameID = scanner.nextInt();
        System.out.println("player color: ");
        String playerColor = scanner.next();
        try{
            serverFacade.joinGame(gameID,playerColor);
            System.out.println("successfully joined");
            GamePlay playgame = new GamePlay(scanner,serverFacade);
        } catch (ResponseException e){

        }

    }
    public void observeGame(){

    }
    public void quit(){
        // exit the program
        System.exit(0);
        System.out.println("Exit");
    }

}
