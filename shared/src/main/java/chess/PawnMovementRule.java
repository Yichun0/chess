package chess;

import java.util.HashSet;

public class PawnMovementRule {
    public HashSet<ChessMove> PawnClass(ChessBoard board, ChessPosition startPosition) {
        HashSet<ChessMove> PawnMoves = new HashSet<>();
        int row = startPosition.getRow();
        int col = startPosition.getColumn();
        int upRow = startPosition.getRow() + 1;
        int downRow = startPosition.getRow() - 1;
        ChessPosition downLeft = new ChessPosition(row - 1, col - 1);
        ChessPosition downRight = new ChessPosition(row - 1, col + 1);
        ChessPosition upLeft = new ChessPosition(row + 1, col - 1);
        ChessPosition upRight = new ChessPosition(row + 1, col + 1);
        // moves for the first line Pawns
        if (row == 2 || row == 6) {
            ChessPosition firstRowUp = new ChessPosition(row + 2, col);
            PawnMoves.add(new ChessMove(startPosition, firstRowUp, null));
            ChessPosition firstRowDown = new ChessPosition(row - 2, col);
            PawnMoves.add(new ChessMove(startPosition, firstRowDown,null));
        }
        // moves when there's an enemy diagonally
        if (EnemyMoves(board, startPosition, downLeft)) {
            PawnMoves.add(new ChessMove(startPosition, downLeft, null));
        }
        if (EnemyMoves(board, startPosition, downRight)) {
            PawnMoves.add(new ChessMove(startPosition, downRight,null));
        }
        if (EnemyMoves(board, startPosition, upLeft)) {
            PawnMoves.add(new ChessMove(startPosition, upLeft, null));
        }
        if (EnemyMoves(board, startPosition, upRight)) {
            PawnMoves.add(new ChessMove(startPosition, upRight, null));
        }
        // check whether it's at the end of the board
        else if (upRow == 8 || downRow == 1) {
            ChessPosition upPosition = new ChessPosition(upRow, col);
            ChessPosition downPosition = new ChessPosition(downRow, col);
            if (board.getPiece(upPosition) == null) {
                // add the moves to the collection
                new ChessMove(startPosition, upPosition, ChessPiece.PieceType.QUEEN);
                new ChessMove(startPosition, upPosition, ChessPiece.PieceType.BISHOP);
                new ChessMove(startPosition, upPosition, ChessPiece.PieceType.ROOK);
                new ChessMove(startPosition, upPosition, ChessPiece.PieceType.KNIGHT);
            } else if (board.getPiece(downPosition) == null) {
                new ChessMove(startPosition, downPosition, ChessPiece.PieceType.QUEEN);
                new ChessMove(startPosition, downPosition, ChessPiece.PieceType.BISHOP);
                new ChessMove(startPosition, downPosition, ChessPiece.PieceType.ROOK);
                new ChessMove(startPosition, downPosition, ChessPiece.PieceType.KNIGHT);
            }
        }
            return PawnMoves;
    }
    private Boolean EnemyMoves(ChessBoard board, ChessPosition current, ChessPosition next) {
        ChessPiece nextpiece = board.getPiece(next);
        ChessPiece currentPiece = board.getPiece(current);
        if (nextpiece == null){
            return false;
        } else if (nextpiece.getTeamColor() != currentPiece.getTeamColor()){
            return true;
        } else{
            return false;
        }
    }
}

