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
        for (int i = 0; i < colPossibleMoves.length; i++) {
            int newRow = row + rowPossibleMoves[i];
            int newCol = column + colPossibleMoves[i];
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            if (newRow <= 8 && newRow >= 1 && newCol <= 8 && newCol >= 1) {
                ChessPiece currentPiece = board.getPiece(startPosition);
                ChessPiece nextPiece = board.getPiece(newPosition);
                if (nextPiece == null) {
                    KingMoves.add(new ChessMove(startPosition, newPosition, null));
                } else if (currentPiece.getTeamColor().equals(nextPiece.getTeamColor())) {
                    continue;
                } else {
                    KingMoves.add(new ChessMove(startPosition, newPosition, null));
                }
            } else {
                break;
            }

        }
        return KingMoves;
    }
}


