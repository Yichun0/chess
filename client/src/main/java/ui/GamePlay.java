package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.ResponseException;
import ui.EscapeSequences;
import ui.WebSocket.ServerMessageHandler;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GamePlay implements ServerMessageHandler {
    private Scanner scanner;
    private ServerFacade serverFacade;
    private String playerColor;
    public GamePlay(Scanner scanner, ServerFacade server, String playercolor){
        this.scanner = new Scanner(System.in);
        this.serverFacade = server;
        this.playerColor = playercolor;
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
                case "Redraw Chess Board" -> redrawBoard();
                case "Leave" -> leave();
                case "Make Move" -> makeMoves();
                case "Resign"-> resign(); // forfeits the game
                case "HighLight Legal Moves" -> highLightMoves();
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
        // draw a new board? or redraw the existing board?
        CreateBoard.drawColorBoard(playerColor);
    }
    public void leave(){

    }
    public void makeMoves(){

    }
    public void resign(){

    }
    public void highLightMoves(){

    }


    @Override
    public void notify(ServerMessage serverMessage) {

    }
}
