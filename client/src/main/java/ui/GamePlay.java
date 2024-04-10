package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.ResponseException;
import ui.EscapeSequences;
import ui.WebSocket.ServerMessageHandler;
import ui.WebSocket.WebSocketFacade;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GamePlay implements ServerMessageHandler {
    private Scanner scanner;
    private ServerFacade serverFacade;
    private String playerColor;
    private WebSocketFacade webSocketFacade;
    public static ChessBoard board;
    private int gameID;

    public GamePlay(Scanner scanner, ServerFacade server, String playerColor, int gameID){
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

    public void redrawBoard(){
       CreateBoard.drawGeneralBoard(board,playerColor);

    }
    public void leave() throws ResponseException {
        System.out.println("You are leaving the game");
        WebSocketFacade.leave(serverFacade.authToken,gameID);
        PostLogin postLogin = new PostLogin(scanner,serverFacade);
        postLogin.help();

    }
    public void makeMoves(){

    }
    public void resign(){
        System.out.println("Are you sure you want to resign this game: T/F");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("T")){
            System.out.println("Game ended");
        }
        else{
            try{
                run();
            } catch (ResponseException e){
                System.out.println(e.getMessage());
            }
        }


    }
    public void highLightMoves(){

    }

    @Override
    public void notify(ServerMessage serverMessage) {

    }
}
