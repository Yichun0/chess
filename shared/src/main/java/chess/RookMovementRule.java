package chess;

import java.util.Collection;
import java.util.HashSet;
import chess.ChessPosition;
import chess.ChessBoard;

public class RookMovementRule {
    private HashSet<ChessMove> RookMoves = new HashSet<>();
    public Collection<ChessMove> RookMovement(ChessBoard board, ChessPosition startPosition) {
            up(board, startPosition);
            down(board, startPosition);
            left(board,startPosition);
            right(board, startPosition);
            return RookMoves;
    }
    private void up(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow() + 1 ;
        int column = startPosition.getColumn();
        while(row <= 8 ){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row,column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null){
                RookMoves.add(new ChessMove(startPosition, newPosition,null));
                row = row + 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                ChessPosition nextPosition =  new ChessPosition(row + 1, column);
                RookMoves.add(new ChessMove(startPosition, nextPosition,null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
    private void down(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow() - 1 ;
        int column = startPosition.getColumn();
        // will this also get updated by the while loop?
        while(row <= 8 ){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row,column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null){
                RookMoves.add(new ChessMove(startPosition, newPosition,null));
                row = row - 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                ChessPosition nextPosition =  new ChessPosition(row - 1, column);
                RookMoves.add(new ChessMove(startPosition, nextPosition,null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
    private void left(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow() ;
        int column = startPosition.getColumn() - 1;
        while(column >= 1){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row,column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null){
                RookMoves.add(new ChessMove(startPosition, newPosition,null));
                column = column - 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                ChessPosition nextPosition =  new ChessPosition(column - 1, column);
                RookMoves.add(new ChessMove(startPosition, nextPosition,null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
    private void right(ChessBoard board, ChessPosition startPosition){
        int row = startPosition.getRow();
        int column = startPosition.getColumn() + 1;
        while(column <= 8){
            ChessPiece currentPiece = board.getPiece(startPosition);
            ChessPosition newPosition = new ChessPosition(row,column);
            ChessPiece newPiece = board.getPiece(newPosition);
            if (board.getPiece(newPosition) == null){
                RookMoves.add(new ChessMove(startPosition, newPosition,null));
                column = column + 1;
            } else if (currentPiece.getTeamColor() != newPiece.getTeamColor()){
                ChessPosition nextPosition =  new ChessPosition(column + 1, column);
                RookMoves.add(new ChessMove(startPosition, nextPosition,null));
                break;
                // do I need to add this move to my RookMoves?
            } else {
                break;
            }
        }
    }
}

