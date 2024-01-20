package chess;

import java.util.HashSet;

public class QueenMovementRule {
    private HashSet<ChessMove> QueenMoves = new HashSet<>();
    private void MoveStright(ChessBoard board, ChessPosition current){

    }
    private void MoveDiagnol(ChessBoard board, ChessPosition current){

    }
    private void noBlockMoves(HashSet<ChessMove> movement, ChessBoard board, ChessPosition current, ChessPosition next) {
        ChessPiece nextpiece = board.getPiece(next);
        ChessPiece currentPiece = board.getPiece(current);
        if (nextpiece == null) {
            movement.add(new ChessMove(current, next, null));
        }
    }
    private void EnemyMoves(HashSet<ChessMove> movement, ChessBoard board, ChessPosition current, ChessPosition next) {
        ChessPiece nextpiece = board.getPiece(next);
        ChessPiece currentPiece = board.getPiece(current);
        if (currentPiece.getTeamColor() != nextpiece.getTeamColor()) {
            movement.add(new ChessMove(current, next, null));
        }
    }

}
