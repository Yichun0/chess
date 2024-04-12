package ui;

import chess.*;
import exception.DataAccessException;
import exception.ResponseException;
import ui.WebSocket.WebSocketFacade;

import java.util.Collection;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GamePlay{
    private Scanner scanner;
    private ServerFacade serverFacade;
    private String playerColor;
    private WebSocketFacade webSocketFacade = new WebSocketFacade();
    public static ChessBoard board;
    private int gameID;
    private ChessGame chessGame = new ChessGame();

    public GamePlay(ServerFacade server, String playerColor, int gameID) throws ResponseException {
        this.scanner = new Scanner(System.in);
        this.serverFacade = server;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }
    public void run() throws ResponseException, DataAccessException {
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
                case "make move" -> makeMove();
                case "resign"-> resign(); // forfeits the game
                case "highlight legal moves" -> highLightMoves();
                default -> run();
            }
        }
    }
    public void help() throws ResponseException, DataAccessException {
        // display text informing user actions
        System.out.println("Choose on of the options to start:");
        run();
    }

    public void redrawBoard() throws ResponseException, DataAccessException {
       CreateBoard.drawGeneralBoard(board,playerColor);
       this.run();

    }
    public void leave() throws ResponseException, DataAccessException {
        System.out.println("You are leaving the game\n");
        webSocketFacade.leave(serverFacade.getAuthToken(),gameID);
        PostLogin postLogin = new PostLogin(scanner,serverFacade);
        postLogin.help();

    }
    public void makeMove() throws DataAccessException, ResponseException {
        System.out.println("Enter startPosition, end position: [row][col] [row][col]");
        String[] answer = scanner.nextLine().toLowerCase().split(" ");
        String[] start= answer[0].split("");
        ChessPosition startPosition=new ChessPosition(parseInt(start[0]), parseInt(start[1]));
        String[] end=answer[1].toLowerCase().split("");
        ChessPosition endPosition=new ChessPosition(parseInt(end[0]), parseInt(end[1]));
        ChessMove move=new ChessMove(startPosition, endPosition, null);
        try {
            chessGame.makeMove(move);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());

        }
        webSocketFacade.makeMove(serverFacade.authToken, gameID,move);
        redrawBoard();
    }


    public void resign() throws ResponseException, DataAccessException {
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
        System.out.println("Enter highlight position: [row][col]");
        String[] answer = scanner.nextLine().toLowerCase().split(" ");
        String[] start= answer[0].split("");
        ChessPosition startPosition=new ChessPosition(parseInt(start[0]), parseInt(start[1]));
        Collection<ChessMove> validMoves = chessGame.validMoves(startPosition);
        CreateBoard.highLightMoves(startPosition,board,playerColor,validMoves);
    }
}
