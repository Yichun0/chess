package MovementRules;

import chess.*;

import java.util.HashSet;

public class PawnMovementRule {
    public HashSet<ChessMove> pawnClass(ChessBoard board, ChessPosition startPosition) {
        HashSet<ChessMove> PawnMoves = new HashSet<>();
        int row = startPosition.getRow();
        int col = startPosition.getColumn();
        int upRow = startPosition.getRow() + 1;
        int downRow = startPosition.getRow() - 1;
        ChessPiece currentPiece = board.getPiece(startPosition);
        ChessPosition upPosition = new ChessPosition(upRow, col);
        ChessPosition downPosition = new ChessPosition(downRow, col);;
        if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition upLeft = new ChessPosition(row + 1, col - 1);
            ChessPosition upRight = new ChessPosition(row + 1, col + 1);
            // white piece moves up
            if (row == 2) {
                ChessPosition firstRowUp = new ChessPosition(row + 2, col);
                if (board.getPiece(upPosition) == null) {
                    PawnMoves.add(new ChessMove(startPosition, upPosition, null));
                }
                if (board.getPiece(firstRowUp) == null) {
                    PawnMoves.add(new ChessMove(startPosition, firstRowUp, null));
                }
            } else if (upRow == 8) {
                // add the moves to the collection
                PawnMoves.add(new ChessMove(startPosition, upPosition, ChessPiece.PieceType.QUEEN));
                PawnMoves.add(new ChessMove(startPosition, upPosition, ChessPiece.PieceType.BISHOP));
                PawnMoves.add(new ChessMove(startPosition, upPosition, ChessPiece.PieceType.ROOK));
                PawnMoves.add(new ChessMove(startPosition, upPosition, ChessPiece.PieceType.KNIGHT));
                if (enemyMoves(board, startPosition, upLeft)) {
                    PawnMoves.add(new ChessMove(startPosition, upLeft, null));
                }
                if (enemyMoves(board, startPosition, upRight)) {
                    PawnMoves.add(new ChessMove(startPosition, upRight, null));
                }
            }

            if (upRow != 8) {
                if (board.getPiece(upPosition) == null) {
                    PawnMoves.add(new ChessMove(startPosition, upPosition, null));
                }
                if (col - 1 >= 1 && enemyMoves(board, startPosition, upLeft)) {
                    PawnMoves.add(new ChessMove(startPosition, upLeft, null));
                }
                if (col + 1 <=8 && enemyMoves(board, startPosition, upRight)) {
                    PawnMoves.add(new ChessMove(startPosition, upRight, null));
                }
            }
        }
            // moves for the first line Pawns
            else if (currentPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                ChessPosition downLeft = new ChessPosition(row - 1, col - 1);
                ChessPosition downRight = new ChessPosition(row - 1, col + 1);
                // move down
                if (row == 7) {
                    ChessPosition firstRowDown = new ChessPosition(row - 2, col);
                    if (board.getPiece(downPosition) == null) {
                        PawnMoves.add(new ChessMove(startPosition, downPosition, null));
                    }
                    if (board.getPiece(downPosition) == null &&board.getPiece(firstRowDown) == null) {
                        PawnMoves.add(new ChessMove(startPosition, firstRowDown, null));
                    }
                } else if (downRow == 1) {
                    // add the moves to the collection
                    if (enemyMoves(board, startPosition, downLeft)) {
                        PawnMoves.add(new ChessMove(startPosition, downLeft, ChessPiece.PieceType.QUEEN));
                        PawnMoves.add(new ChessMove(startPosition, downLeft, ChessPiece.PieceType.BISHOP));
                        PawnMoves.add(new ChessMove(startPosition, downLeft, ChessPiece.PieceType.ROOK));
                        PawnMoves.add(new ChessMove(startPosition, downLeft, ChessPiece.PieceType.KNIGHT));
                    }
                    if (enemyMoves(board, startPosition, downRight)) {
                        PawnMoves.add(new ChessMove(startPosition, downRight, ChessPiece.PieceType.QUEEN));
                        PawnMoves.add(new ChessMove(startPosition, downRight, ChessPiece.PieceType.BISHOP));
                        PawnMoves.add(new ChessMove(startPosition, downRight, ChessPiece.PieceType.ROOK));
                        PawnMoves.add(new ChessMove(startPosition, downRight, ChessPiece.PieceType.KNIGHT));
                    }
                    PawnMoves.add(new ChessMove(startPosition, downPosition, ChessPiece.PieceType.QUEEN));
                    PawnMoves.add(new ChessMove(startPosition, downPosition, ChessPiece.PieceType.BISHOP));
                    PawnMoves.add(new ChessMove(startPosition, downPosition, ChessPiece.PieceType.ROOK));
                    PawnMoves.add(new ChessMove(startPosition, downPosition, ChessPiece.PieceType.KNIGHT));

                }
                if (downRow != 1) {
                    if (board.getPiece(downPosition) == null) {
                        PawnMoves.add(new ChessMove(startPosition, downPosition, null));
                    }
                    if (col -1 >= 1 && enemyMoves(board, startPosition, downLeft)) {
                        PawnMoves.add(new ChessMove(startPosition, downLeft, null));
                    }
                    if (col + 1 <= 8 && enemyMoves(board, startPosition, downRight)) {
                        PawnMoves.add(new ChessMove(startPosition, downRight, null));
                    }
                }
                // check whether it's at the end of the board
            }

        return PawnMoves;
    }
        private Boolean enemyMoves (ChessBoard board, ChessPosition current, ChessPosition next){
            ChessPiece nextpiece = board.getPiece(next);
            ChessPiece currentPiece = board.getPiece(current);
            if (nextpiece == null) {
                return false;
            } else return nextpiece.getTeamColor() != currentPiece.getTeamColor();
        }

}


