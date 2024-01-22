package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMovementRule {
    private HashSet<ChessMove> BishopMoves = new HashSet<>();

    public HashSet<ChessMove> BishopMovement(ChessBoard board, ChessPosition startPosition) {
        upRight(board, startPosition);
        upLeft(board, startPosition);
        downLeft(board, startPosition);
        downRight(board, startPosition);
        return BishopMoves;
    }

    private void upRight(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow() + 1;
        int column = startPosition.getColumn() + 1;
        while (row <= 8 && column <= 8) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                BishopMoves.add(new ChessMove(startPosition, newPosition, null));
                startPosition = newPosition;
                row += 1;
                column += 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row + 1, column + 1);
                BishopMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
            } else {
                break;
            }
        }
    }

    private void upLeft(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow() - 1;
        int column = startPosition.getColumn() + 1;
        // will this also get updated by the while loop?
        while (row >= 1 && column <= 8) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                BishopMoves.add(new ChessMove(startPosition, newPosition, null));
                startPosition = newPosition;
                row -= 1;
                column += 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row - 1, column + 1);
                BishopMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }

    private void downLeft(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow() - 1;
        int column = startPosition.getColumn() - 1;
        // will this also get updated by the while loop?
        while (row >= 1 && column >= 1) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                BishopMoves.add(new ChessMove(startPosition, newPosition, null));
                startPosition = newPosition;
                row -= 1;
                column -= 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row - 1, column - 1);
                BishopMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }

    private void downRight(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow() + 1;
        int column = startPosition.getColumn() - 1;
        // will this also get updated by the while loop?
        while (row <= 8 && column >= 1) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                BishopMoves.add(new ChessMove(startPosition, newPosition, null));
                startPosition = newPosition;
                row += 1;
                column -= 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row + 1, column - 1);
                BishopMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
}

