package chess;

import java.util.HashSet;
import java.util.Set;

public class KingMovementRule {
    public HashSet<ChessMove> kingChess(ChessBoard board, ChessPosition startPosition) {

        HashSet<ChessMove> KingMoves = new HashSet<>();
        int row = startPosition.getRow();
        int column = startPosition.getColumn();
        // create all new postions for all the moves
        ChessPosition downLeft = new ChessPosition(row - 1, column - 1);
        ChessPosition downRight = new ChessPosition(row - 1, column + 1);
        ChessPosition down = new ChessPosition(row - 1, column);
        ChessPosition upLeft = new ChessPosition(row + 1, column - 1);
        ChessPosition upRight = new ChessPosition(row + 1, column + 1);
        ChessPosition up = new ChessPosition(row + 1, column);
        ChessPosition left = new ChessPosition(row, column - 1);
        ChessPosition right = new ChessPosition(row, column + 1);
        // check if there is another piece in the position
        if (NoExistingPiece(board, downLeft)) {
            //check if it's the end of the board
            if (row >= 1 || column >= 1) {
                KingMoves.add(new ChessMove(startPosition, downLeft, null));
            }
        }
        if (NoExistingPiece(board, downRight)) {
            //check if it's the end of the board
            if (row >= 1 || column <= 8) {
                KingMoves.add(new ChessMove(startPosition, downRight, null));
            }
        }
        if (NoExistingPiece(board, down)) {
            //check if it's the end of the board
            if (row >= 1 || column <= 8 && column >= 1) {
                KingMoves.add(new ChessMove(startPosition, down, null));
            }
        }
        if (NoExistingPiece(board, upLeft)) {
            //check if it's the end of the board
            if (row <= 8 || column >= 1) {
                KingMoves.add(new ChessMove(startPosition, upLeft, null));
            }
        }
        if (NoExistingPiece(board, upRight)) {
            //check if it's the end of the board
            if (row <= 8 || column <= 8) {
                KingMoves.add(new ChessMove(startPosition, upRight, null));
            }
        }
        if (NoExistingPiece(board, up)) {
            //check if it's the end of the board
            if (row <= 8 || column <= 8 && column >= 1) {
                KingMoves.add(new ChessMove(startPosition, up, null));
            }
        }
        if (NoExistingPiece(board, left)) {
            //check if it's the end of the board
            if (row >= 1 && row <= 8 || column >= 1) {
                KingMoves.add(new ChessMove(startPosition, left, null));
            }
        }
        if (NoExistingPiece(board, right)) {
            //check if it's the end of the board
            if (row >= 1 && row <= 8 || column <= 8) {
                KingMoves.add(new ChessMove(startPosition, downLeft, null));
            }
        }
        // add all the moves unto the hashset
        return KingMoves;

    }
    private boolean NoExistingPiece(ChessBoard board, ChessPosition position){
        ChessPiece Piece = board.getPiece(position);
        if (Piece == null){
            return true;
        }else {
            return false;
        }
    }
}


