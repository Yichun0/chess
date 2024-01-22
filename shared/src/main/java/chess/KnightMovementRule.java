package chess;

import java.util.HashSet;

public class KnightMovementRule {
    public HashSet<ChessMove> KnightClass(ChessBoard board, ChessPosition startPosition) {
        HashSet<ChessMove> KnightMoves = new HashSet<>();
        int[] rowPossibleMoves = {-1, -1, +1, +1, -2, -2, +2, +2};
        int[] colPossibleMoves = {+2, -2, +2, -2, +1, -1, +1, -1};
        int row = startPosition.getRow();
        int col = startPosition.getColumn();
        for (int i = 0; i < rowPossibleMoves.length; i++) {
            int nextRow = row + rowPossibleMoves[i];
            int nextCol = col + colPossibleMoves[i];
            if (nextRow <= 8 && nextRow >= 1 && nextCol <= 8 && nextCol >= 1) {
                ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                ChessPiece nextPiece = board.getPiece(nextPosition);
                if (nextPiece == null) {
                    KnightMoves.add(new ChessMove(startPosition, nextPosition, null));
                } else if (currentPiece.getTeamColor() != nextPiece.getTeamColor()) {
                    KnightMoves.add(new ChessMove(startPosition, nextPosition, null));
                } else {
                    continue;
                }
            }
        }
        return KnightMoves;
    }
}
