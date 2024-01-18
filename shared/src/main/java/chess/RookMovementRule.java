package chess;

import java.util.Collection;
import java.util.HashSet;
import chess.ChessPosition;
import chess.ChessBoard;

public class RookMovementRule {
    private HashSet<ChessPosition> RookMoves = new HashSet<>();
    public Collection<ChessPosition> RookMovement(ChessBoard board, ChessPosition startPosition) {
            up(board, startPosition);
            down(board, startPosition);
            left(board,startPosition);
            right(board, startPosition);
            return RookMoves;
    }
    private void up(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow() + 1 ;
        int column = startPosition.getColumn();
        ChessPosition nextPosition = new ChessPosition(row,column);
        // will this also get updated by the while loop?
        while(row <=8 ){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPiece newPiece = board.getPiece(nextPosition);
            if (board.getPiece(nextPosition) == null){
                RookMoves.add(nextPosition);
                row = row + 1;
                nextPosition = new ChessPosition(row, column);
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                newPiece = currentPiece;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
    private void down(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow() - 1 ;
        int column = startPosition.getColumn();
        ChessPosition nextPosition = new ChessPosition(row,column);
        // will this also get updated by the while loop?
        while(row >= 1){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPiece newPiece = board.getPiece(nextPosition);
            if (board.getPiece(nextPosition) == null){
                RookMoves.add(nextPosition);
                row = row - 1;
                nextPosition = new ChessPosition(row, column);
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                newPiece = currentPiece;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
    private void left(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow() ;
        int column = startPosition.getColumn() - 1;
        ChessPosition nextPosition = new ChessPosition(row,column);
        // will this also get updated by the while loop?
        while(column >= 1 ){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPiece newPiece = board.getPiece(nextPosition);
            if (board.getPiece(nextPosition) == null){
                RookMoves.add(nextPosition);
                column = column - 1;
                nextPosition = new ChessPosition(row, column);
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                newPiece = currentPiece;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
    private void right(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow();
        int column = startPosition.getColumn() + 1;
        ChessPosition nextPosition = new ChessPosition(row,column);
        // will this also get updated by the while loop?
        while(column <=8 ){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPiece newPiece = board.getPiece(nextPosition);
            if (board.getPiece(nextPosition) == null){
                RookMoves.add(nextPosition);
                column = column + 1;
                nextPosition = new ChessPosition(row, column);
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                newPiece = currentPiece;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
}

