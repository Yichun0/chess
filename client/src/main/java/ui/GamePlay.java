package ui;

import chess.*;
import exception.ResponseException;
import ui.EscapeSequences;
import ui.WebSocket.ServerMessageHandler;
import ui.WebSocket.WebSocketFacade;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GamePlay{
    private Scanner scanner;
    private ServerFacade serverFacade;
    private String playerColor;
    private WebSocketFacade webSocketFacade = new WebSocketFacade();
    public static ChessBoard board;
    private int gameID;

    public GamePlay(ServerFacade server, String playerColor, int gameID) throws ResponseException {
        this.scanner = new Scanner(System.in);
        this.serverFacade = server;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }
    public void run() throws ResponseException {
        System.out.println("Welcome to the chess game.");
        System.out.println("""
                    Help
                    Redraw Chess Board
                    Leave
                    Make Move
                    Resign
                    HighLight Legal Moves
                """);
        while(true) {
            System.out.print("play a game >>> " +"\n");
            String command = scanner.nextLine();
            switch (command) {
                case "help" -> help();
                case "redraw chess board" -> redrawBoard();
                case "leave" -> leave();
                case "make move" -> makeMoves();
                case "resign"-> resign(); // forfeits the game
                case "highLight legal moves" -> highLightMoves();
                default -> run();
            }
        }
    }
    public void help() throws ResponseException {
        // display text informing user actions
        System.out.println("Choose on of the options to start:");
        run();
    }

    public void redrawBoard() throws ResponseException {
       CreateBoard.drawGeneralBoard(board,playerColor);
       this.run();

    }
    public void leave() throws ResponseException {
        System.out.println("You are leaving the game\n");
        webSocketFacade.leave(serverFacade.getAuthToken(),gameID);
        PostLogin postLogin = new PostLogin(scanner,serverFacade);
        postLogin.help();

    }
    public void makeMoves(){
        System.out.println("Enter the row of your start position:  ");
        var startRow = scanner.nextInt();
        System.out.println("Enter the column of your start position:  ");
        var startCol = scanner.nextInt();
        ChessPosition startPosition = new ChessPosition(startRow,startCol);
        System.out.println("Enter the row of your end position:  ");
        var endRow = scanner.nextInt();
        System.out.println("Enter the column of your end position:  ");
        var endCol = scanner.nextInt();
        ChessPosition endPosition = new ChessPosition(endRow,endCol);
        ChessMove move = new ChessMove(startPosition,endPosition,null);
        webSocketFacade.makeMove(serverFacade.getAuthToken(), gameID,move);
//        ChessMove move = new ChessMove();


    }
    public void resign() throws ResponseException {
        System.out.println("Are you sure you want to resign this game: True/False");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("true")){
            try{
            webSocketFacade.resign(serverFacade.getAuthToken(),gameID);}
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("You have successfully resigned");
            PostLogin postLogin = new PostLogin(scanner,serverFacade);
            postLogin.help();
        }
        else{
            System.out.println("");
        }


    }
    public void highLightMoves(){

    }
}
