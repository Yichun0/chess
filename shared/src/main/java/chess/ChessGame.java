package chess;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessGame.TeamColor team;
    private ChessBoard board = new ChessBoard();
    private ChessPiece[][] borad = new ChessPiece[8][8];
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> validMoves = new HashSet<>();
        ChessPiece currentPeice = board.getPiece(startPosition);
        if (currentPeice == null){
            return null;
        }
        Collection<ChessMove> possibleMoves = currentPeice.pieceMoves(board,startPosition);
        for (ChessMove move:possibleMoves){
            ChessPosition start = move.getStartPosition();
            ChessPosition end = move.getEndPosition();
            ChessBoard copyBoard = copiedBoard(board);
            copyBoard.removePiece(start,currentPeice);
            copyBoard.addPiece(end,currentPeice);
            this.board = copyBoard;
            if (!isInCheck(currentPeice.getTeamColor())){
                if (currentPeice.pieceColor == team){
                    validMoves.add(move);
                }
            }
            this.board = board;
        }
        return validMoves;
    }
    private ChessBoard copiedBoard(ChessBoard board) {
        ChessBoard copyBoard = new ChessBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row,col);
                ChessPiece cururentPiece = board.getPiece(currentPosition);
                if (cururentPiece != null){
                    copyBoard.addPiece(currentPosition,cururentPiece);
                }
            }
        }
        return copyBoard;
    }
    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPiece currentPiece = board.getPiece(start);
        Collection<ChessMove> validMoves =  validMoves(start);
        if (!validMoves.contains(move)){
            throw new InvalidMoveException("Illegal Moves");
        }
        else{
            // make the move on the board

        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = getKingPosition(teamColor);
        Collection<ChessMove> pieceMoves = allOtherPieceMoves(teamColor);
            for (ChessMove move:pieceMoves){
                if (move.getEndPosition() == kingPosition){
                    return true;
            }
        }

        return false;
    }
    private ChessPosition getKingPosition(TeamColor teamColor){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                if (currentPiece != null && board.getPiece(currentPosition).getPieceType() == ChessPiece.PieceType.KING) {
                    if (currentPiece.getTeamColor() == teamColor) {
                        return currentPosition;
                    }
                }
            }
        }
        return null;

    }
    private Collection<ChessMove> allOtherPieceMoves(TeamColor teamcolor) {
        // a collection with all the moves other than king on the other team
        Collection <ChessMove> allOtherPieceMoves = new HashSet<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                if (currentPiece.getTeamColor() != teamcolor && currentPiece.getPieceType() != ChessPiece.PieceType.KING) {
                    Collection<ChessMove> pieceMoves = currentPiece.pieceMoves(board, currentPosition);
                    allOtherPieceMoves.addAll(pieceMoves);
                }
            }
        }
        return allOtherPieceMoves;
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamcolor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamcolor) {
        ChessPiece king = new ChessPiece(teamcolor, ChessPiece.PieceType.KING);
        // block the piece
        // if all valid moves
        // all the move --> check king in check
        // copy board --> array copyOf() --> valid == empty
        // move the king
        // kill the piece






        // check to see whether other piece will get the king out of danger
        if (teamcolor == TeamColor.BLACK){
            TeamColor otherTeam = TeamColor.WHITE;
            Collection<ChessMove> otherPieceMoves = allOtherPieceMoves(TeamColor.WHITE);
            for (ChessMove moves : otherPieceMoves){
                if (!isInCheck(teamcolor)){
                    return false;
                }
            }
        } else if (teamcolor == TeamColor.WHITE){
            TeamColor otherTeam = TeamColor.BLACK;
            Collection<ChessMove> otherPieceMoves = allOtherPieceMoves(TeamColor.BLACK);
            for (ChessMove moves : otherPieceMoves){
                if (!isInCheck(teamcolor)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // every piece can't move and return true
        // not in check
        // no valid move for all pieces on the board
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
