package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.EscapeSequences;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GamePlay {
//    private String serverUrl;
//    private Scanner scanner;
//    private ChessBoard board;
//    private String boardEdgeColor = SET_BG_COLOR_DARK_GREY;
//    private final String whiteSpace = SET_BG_COLOR_WHITE;
//    private final String blackSpace = SET_BG_COLOR_BLACK;
//    public GamePlay(Scanner scanner, ServerFacade server) {
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void drawBoard() {
//        // provide chessboard class
//        // iterate over
//        // print piece if there is a piece
//        ChessBoard board = new ChessBoard();
//
//        board.resetBoard();
//        GamePlay.chessboard(board);
//
//    }
//    public static void main (String [] arg){
//
//    }
//
//    public static chessboard(ChessBoard board) {
//        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//        String [] side = new String[] {"1", "2", "3", "4", "5", "6", "7", "8"};
//        String [] headers = new String[] {"a", "b", "c", "d", "e", "f", "g", "h"};
//        drawside(out,side);
//        drawside(out,headers);
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
//                if (currentPiece.getPieceType().equals(ChessPiece.PieceType.KING)) {
//                    System.out.println("K");
//                }
//                if (currentPiece.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
//                    System.out.println("Q");
//                }
//                if (currentPiece.getPieceType().equals(ChessPiece.PieceType.BISHOP)) {
//                    System.out.println("B");
//                }
//                if (currentPiece.getPieceType().equals(ChessPiece.PieceType.ROOK)) {
//                    System.out.println("R");
//                }
//                if (currentPiece.getPieceType().equals(ChessPiece.PieceType.PAWN)) {
//                    System.out.println("P");
//                }
//                if (currentPiece.getPieceType().equals(ChessPiece.PieceType.KNIGHT)) {
//                    System.out.println("N");
//                }
//            }
//        }
//    }
//
//    private void drawside(PrintStream out, String[] header){
//        out.print(boardEdgeColor);
//        out.print("\u2003");
//        out.print(SET_TEXT_BOLD);
//        out.print(SET_TEXT_COLOR_BLACK);
//
//        // creating spaces between each letter
//        for (String letter: header){
//            out.print("\u2006");
//            out.print(letter);
//            out.print("\u2006");
//        }
//
//    }
//
//
//

}
