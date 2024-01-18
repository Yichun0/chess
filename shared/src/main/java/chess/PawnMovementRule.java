package chess;

import java.util.HashSet;

public class PawnMovementRule {
    public HashSet<ChessPosition> PawnClass (ChessBoard board, ChessPosition startPosition){
        HashSet<ChessPosition> PawnMoves = new HashSet<ChessPosition>();
        int row = startPosition.getRow();
        int col = startPosition.getColumn();
        ChessPiece currentPiece = board.getPiece(startPosition);
        ChessPosition downLeft = new ChessPosition(row - 1, col - 1);
        ChessPosition downRight = new ChessPosition(row - 1, col + 1);
        ChessPosition upLeft = new ChessPosition(row + 1, col - 1);
        ChessPosition upRight = new ChessPosition(row + 1, col + 1);
        ChessPiece newPiece = board.getPiece(nextPosition);
        if (row == 2 || row == 6){
            ChessPosition firstRowUp = new ChessPosition(row + 2, col);
            PawnMoves.add(firstRowUp);
            ChessPosition firstRowDown = new ChessPosition(row - 2, col);
            PawnMoves.add(firstRowDown);
        } else if () {
            
        }
        return PawnMoves;
    }

}
