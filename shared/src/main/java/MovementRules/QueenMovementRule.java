package MovementRules;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class QueenMovementRule {
    HashSet<ChessMove> queenMoves = new HashSet<>();
    public HashSet<ChessMove> queenChess(ChessBoard board, ChessPosition startPosition) {
        moveStright(board, startPosition);
        moveDiagnol(board, startPosition);
        return queenMoves;
    }
    private void moveStright(ChessBoard board, ChessPosition startPosition) {
        RookMovementRule rookmoves = new RookMovementRule();
        queenMoves.addAll(rookmoves.rookMovement(board, startPosition));

    }
    private void moveDiagnol(ChessBoard board, ChessPosition current) {
        BishopMovementRule bishopmoves = new BishopMovementRule();
        queenMoves.addAll(bishopmoves.bishopMovement(board, current));
    }
}
