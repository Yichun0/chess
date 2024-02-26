package MovementRules;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class RookMovementRule {
    private HashSet<ChessMove> rookMoves = new HashSet<>();

    public HashSet<ChessMove> rookMovement(ChessBoard board, ChessPosition startPosition) {
        up(board, startPosition);
        down(board, startPosition);
        left(board, startPosition);
        right(board, startPosition);
        return rookMoves;
    }

    private void up(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow() + 1;
        int column = startPosition.getColumn();
        while (row <= 8) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, newPosition, null));
                row = row + 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row, column);
                rookMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }

    private void down(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow() - 1;
        int column = startPosition.getColumn();
        // will this also get updated by the while loop?
        while (row >= 1) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, newPosition, null));
                row = row - 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row, column);
                rookMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }

    private void left(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow();
        int column = startPosition.getColumn() - 1;
        while (column >= 1) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, newPosition, null));
                column = column - 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row, column);
                rookMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }

    private void right(ChessBoard board, ChessPosition startPosition) {
        int row = startPosition.getRow();
        int column = startPosition.getColumn() + 1;
        while (column <= 8) {
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null) {
                rookMoves.add(new ChessMove(startPosition, newPosition, null));
                column = column + 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()) {
                ChessPosition nextPosition = new ChessPosition(row, column);
                rookMoves.add(new ChessMove(startPosition, nextPosition, null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
}

