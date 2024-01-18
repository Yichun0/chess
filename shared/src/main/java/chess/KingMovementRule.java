package chess;

import java.util.HashSet;
import java.util.Set;

public class KingMovementRule {
    public HashSet<ChessPosition> kingChess(ChessBoard board, ChessPosition startPosition) {

        HashSet<ChessPosition> KingMoves = new HashSet<ChessPosition>();
        int row = startPosition.getRow();
        int column = startPosition.getColumn();
        ChessPosition downLeft = new ChessPosition(row - 1, column - 1);
        ChessPosition downRight = new ChessPosition(row - 1, column + 1);
        ChessPosition down = new ChessPosition(row - 1, column);
        ChessPosition upLeft = new ChessPosition(row + 1, column - 1);
        ChessPosition upRight = new ChessPosition(row + 1, column + 1);
        ChessPosition up = new ChessPosition(row + 1, column);
        ChessPosition left = new ChessPosition(row, column - 1);
        ChessPosition right = new ChessPosition(row, column + 1);
        // create all new postions for all the moves
        KingMoves.add(downLeft);
        KingMoves.add(downRight);
        KingMoves.add(down);
        KingMoves.add(upLeft);
        KingMoves.add(upRight);
        KingMoves.add(up);
        KingMoves.add(left);
        KingMoves.add(right);
        // add all the moves unto the hashset
        return KingMoves;

    }
}

//            int updateRow = row + kingMoves[i][0];
//            int updateCol = column + kingMoves[i][1];
//            if (updateRow > 0 && updateRow < 9 && updateCol > 0 && updateCol < 9) {
//                ChessPosition newPosition = new ChessPosition(updateRow, updateCol);
//                ChessPiece newPiece = board.getPiece(newPosition);



// move 1 square in any directions
// remove king from danger
// make the moves
// update the index
// update the chess piece
// king in danger:

