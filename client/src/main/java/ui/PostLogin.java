package ui;

import Model.GameData;
import exception.DataAccessException;
import exception.ResponseException;

import java.util.*;

public class PostLogin {
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
                join game
                observe game
                logout
                quit
                help
                """);
        while(true) {
            System.out.print("play a game >>> " +"\n");
            String command = scanner.nextLine();
            switch (command) {
                case "help" -> help();
                case "logout" -> logout();
                case "create game" -> createGame();
                case "list games" -> listGames();
                case "join game"-> joinGame();
                case "observe game" -> observeGame();
                case "quit" -> quit();
                default -> run();
            }
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
            String gameName = scanner.nextLine();
            serverFacade.createGame(gameName);
            System.out.println("successfully create game" + gameName);
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
            help();
        }
    }
    public void listGames() throws ResponseException {
        int gameIndex = 1;
        try {
            System.out.println("Games: ");
            Collection<GameData> gamelist = serverFacade.listGames();
            List<GameData> games = new ArrayList<>(gamelist);
            for (int i = 0; i < gamelist.size(); i++) {
                System.out.println(gameIndex + i + ". " + games.get(i).getGameName());
                int gameID = games.get(i).getGameID();
                System.out.println("Game ID: " + gameID);
                String blackUsername = games.get(i).getBlackUsername();
                String whiteUsername = games.get(i).getWhiteUsername();
                if (blackUsername != null) {
                    System.out.println("black player: " + blackUsername);
                } else if (whiteUsername != null) {
                    System.out.println("white player: " + whiteUsername);
                }
                System.out.println("\n");
            }
        } catch(ResponseException e){
            System.out.println(e.getMessage());
            help();
        }
    }
    public void joinGame() throws ResponseException {
        Collection<GameData> gamelist = serverFacade.listGames();
        List<GameData> games = new ArrayList<>(gamelist);
        System.out.println("enter the number of the game you want to play: ");
        int gameIndex = scanner.nextInt();
        int gameID = games.get(gameIndex - 1).getGameID();
        System.out.println("player color: ");
        String playerColor = scanner.next();
        if (playerColor == null) {
            observeGame();
        } else {
            try {
                serverFacade.joinGame(gameID, playerColor);
                System.out.println("successfully joined");
                //websocket connection
                GamePlay gamePlay = new GamePlay(scanner,serverFacade,playerColor);
                gamePlay.help();

            } catch (ResponseException e) {
                System.out.println(e.getMessage());
                help();
            }
        }
    }
    public void observeGame() throws ResponseException {
        Collection<GameData> gamelist = serverFacade.listGames();
        List<GameData> games = new ArrayList<>(gamelist);
        System.out.println("enter the number of the game you want to observe: ");
        int gameIndex = scanner.nextInt();
        int gameID = games.get(gameIndex - 1).getGameID();
        try {
            serverFacade.observeGame(gameID);
            System.out.println("successfully joined as observer for " + gameID);
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
            help();
        }
    }
    public void quit(){
        // exit the program
        System.exit(0);
        System.out.println("Exit");
    }

}
