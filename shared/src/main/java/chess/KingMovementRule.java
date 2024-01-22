package chess;

import java.util.HashSet;
import java.util.Set;

public class KingMovementRule {
    public HashSet<ChessMove> kingChess(ChessBoard board, ChessPosition startPosition) {

        HashSet<ChessMove> KingMoves = new HashSet<>();
        int row = startPosition.getRow();
        int column = startPosition.getColumn();
        int[] rowPossibleMoves = {-1, -1, -1, +1, +1, +1, 0, 0};
        int[] colPossibleMoves = {-1, +1, 0, -1, +1, 0, -1, +1};
        while (row >= 1 && row <= 8 && column <= 8 && column >= 1) {
            for (int i = 0; i < colPossibleMoves.length; i++) {
                ChessPosition newPosition = new ChessPosition(rowPossibleMoves[i], colPossibleMoves[i]);
                ChessPiece currentPiece = board.getPiece(startPosition);
                ChessPiece nextPiece =  board.getPiece(newPosition);
                if (currentPiece.getTeamColor().equals(nextPiece.getTeamColor())){
                    break;
                }else{KingMoves.add(new ChessMove(startPosition, newPosition, null));
                }
            }
        }
        return KingMoves;
    }
    private boolean NoExistingPiece(ChessBoard board, ChessPosition position) {
        ChessPiece Piece = board.getPiece(position);
        if (Piece == null) {
            return true;
        } else {
            return false;
        }
    }
}


