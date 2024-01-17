package chess;

import java.util.Set;

public class KingClass {
    ChessPosition position;
    ChessGame.TeamColor pieceColor;
    public KingClass(ChessPosition position, ChessGame.TeamColor pieceColor){
        this.position = position;
        this.pieceColor = pieceColor;
    }
    public Set<ChessMove> kingChess (ChessBoard board, ChessPosition updatePosition){

        int row = position.getRow();
        int column = position.getColumn();
        int [][] kingMoves = {{-1,-1},{-1,0},{-1,1},{0,1},{0,-1},{1,1},{1,0},{1,-1}};
        for (int i = 0; i < kingMoves.length; i++) {
            int updateRow = row + kingMoves[i][0];
            int updateCol = column+ kingMoves[i][1];
            if (updateRow > 0 && updateRow < 9 && updateCol > 0 && updateCol < 9){
                ChessPosition newPosition = new ChessPosition(updateRow,updateCol);
                ChessPiece newPiece = board.getPiece(newPosition);

            }

    };


// move 1 square in any directions
// remove king from danger
// make the moves
// update the index
// update the chess piece
// king in danger:

