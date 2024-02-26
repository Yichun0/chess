package chess;

import MovementRules.KnightMovementRule;
import MovementRules.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessPiece.PieceType type;
    public ChessGame.TeamColor pieceColor;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.pieceColor = pieceColor;
    }


    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "type=" + type +
                ", pieceColor=" + pieceColor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return type == piece.type && pieceColor == piece.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pieceColor);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> allMoves = new HashSet<>();
        if (this.type.equals(PieceType.KING)) {
            KingMovementRule kingmoves = new KingMovementRule();
            allMoves = (kingmoves.kingChess(board, myPosition));

        }
        if (this.type.equals(PieceType.QUEEN)) {
            QueenMovementRule queenMoves = new QueenMovementRule();
            allMoves = queenMoves.queenChess(board, myPosition);
        }
        if (this.type.equals(PieceType.BISHOP)) {
            BishopMovementRule bishopMoves = new BishopMovementRule();
            allMoves = bishopMoves.bishopMovement(board, myPosition);
        }
        if (this.type.equals(PieceType.ROOK)) {
            RookMovementRule rookMoves = new RookMovementRule();
            allMoves = rookMoves.rookMovement(board, myPosition);
        }
        if (this.type.equals(PieceType.PAWN)) {
            PawnMovementRule pawnMoves = new PawnMovementRule();
            allMoves = pawnMoves.pawnClass(board, myPosition);
        }
        if (this.type.equals(PieceType.KNIGHT)) {
            KnightMovementRule knightMoves = new KnightMovementRule();
            allMoves = knightMoves.knightClass(board, myPosition);
        }
        return allMoves;
    }

}
