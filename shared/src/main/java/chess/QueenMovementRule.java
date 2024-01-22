package chess;

import java.util.HashSet;

public class QueenMovementRule {
    HashSet<ChessMove> QueenMoves = new HashSet<>();
    public HashSet<ChessMove> QueenChess(ChessBoard board, ChessPosition startPosition) {
        MoveStright(board, startPosition);
        MoveDiagnol(board, startPosition);
        return QueenMoves;
    }
    private void MoveStright(ChessBoard board, ChessPosition startPosition) {
        RookMovementRule rookmoves = new RookMovementRule();
        QueenMoves.addAll(rookmoves.RookMovement(board, startPosition));

    }
    private void MoveDiagnol(ChessBoard board, ChessPosition current) {
        BishopMovementRule bishopmoves = new BishopMovementRule();
        QueenMoves.addAll(bishopmoves.BishopMovement(board, current));
    }
}
