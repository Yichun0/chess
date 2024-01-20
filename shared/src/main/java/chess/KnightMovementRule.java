package chess;

import java.util.HashSet;

public class KnightMovementRule {
    public HashSet<ChessMove> KnightClass(ChessBoard board, ChessPosition startPosition) {
        HashSet<ChessMove> KnightMoves = new HashSet<>();
        int [] rowPossibleMoves = {-1, -1, +1, +1, -2, -2, +2, +2};
        int [] colPossibleMoves = {+2, -2, +2, -2, +1, -1, +1, -1};
        int row = startPosition.getRow();
        int col = startPosition.getColumn();
        while (row >= 1 && row <= 8 && col <= 8 && col >= 1){
            for (int i = 0; i < rowPossibleMoves.length; i++){
                int nextRow = row + rowPossibleMoves[i];
                int nextCol = col + colPossibleMoves[i];
                ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
                KnightMoves.add(new ChessMove(startPosition, nextPosition,null));
            }
        }
        return KnightMoves;
    }
}
