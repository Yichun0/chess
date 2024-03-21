import chess.*;
import ui.PreLogin;
import ui.ServerFacade;

import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private ServerFacade serverFacade;
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        new PreLogin(new Scanner(System.in), new ServerFacade("http://localhost:8080")).run();
    }
}