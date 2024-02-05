package chess;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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
            ChessBoard copyBoard = copiedBoard(board);
            ChessPosition end = move.getEndPosition();
            copyBoard.removePiece(startPosition);
            copyBoard.addPiece(end,currentPeice);
            ChessBoard tempBoard = this.board;
            this.board = copyBoard;
            if (!isInCheck(currentPeice.getTeamColor())){
                validMoves.add(move);
            }
            this.board = tempBoard;
        }
        return validMoves;
    }
    private ChessBoard copiedBoard(ChessBoard board) {
        ChessBoard copyBoard = new ChessBoard();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
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
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece currentPiece = board.getPiece(startPosition);
        Collection<ChessMove> allValidMoves = validMoves(startPosition);
        if (currentPiece != null && !allValidMoves.contains(move)) {
            throw new InvalidMoveException("Illegal Moves");
        }
        if (currentPiece.getTeamColor()!= team){
            throw new InvalidMoveException("Illegal Moves");
        }
        else {
            board.removePiece(startPosition);
            board.addPiece(endPosition, currentPiece);
            if (isInCheck(currentPiece.getTeamColor())) {
                board.removePiece(endPosition);
                board.addPiece(startPosition, currentPiece);
                throw new InvalidMoveException("Illegal Moves");
            }
            // special promotion
            if (move.getPromotionPiece() != null) {
                ChessPiece.PieceType promotionPieceType = move.getPromotionPiece();
                if (promotionPieceType == ChessPiece.PieceType.ROOK) {
                    ChessPiece PromotionRook = new ChessPiece(currentPiece.getTeamColor(), ChessPiece.PieceType.ROOK);
                    board.addPiece(endPosition, PromotionRook);
                }
                if (promotionPieceType == ChessPiece.PieceType.QUEEN) {
                    ChessPiece PromotionQueen = new ChessPiece(currentPiece.getTeamColor(), ChessPiece.PieceType.QUEEN);
                    board.addPiece(endPosition, PromotionQueen);
                }
                if (promotionPieceType == ChessPiece.PieceType.BISHOP) {
                    ChessPiece PromotionBishop = new ChessPiece(currentPiece.getTeamColor(), ChessPiece.PieceType.BISHOP);
                    board.addPiece(endPosition, PromotionBishop);
                }
                if (promotionPieceType == ChessPiece.PieceType.KNIGHT) {
                    ChessPiece PromotionKnight = new ChessPiece(currentPiece.getTeamColor(), ChessPiece.PieceType.KNIGHT);
                    board.addPiece(endPosition, PromotionKnight);
                }
            }
            if (team == TeamColor.WHITE) {
                team = TeamColor.BLACK;
            } else team = TeamColor.WHITE;
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
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                if (currentPiece != null && currentPiece.getTeamColor() != teamColor) {
                    Collection<ChessMove> pieceMoves = currentPiece.pieceMoves(board, currentPosition);
                    for (ChessMove move : pieceMoves){
                        ChessPosition endPosition = move.getEndPosition();
                        if (endPosition.equals(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private ChessPosition getKingPosition(TeamColor teamColor){
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
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

    @Override
    public String toString() {
        return "ChessGame{" +
                "team=" + team +
                ", board=" + board +
                ", borad=" + Arrays.toString(borad) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return team == chessGame.team && Objects.equals(board, chessGame.board) && Arrays.equals(borad, chessGame.borad);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(team, board);
        result = 31 * result + Arrays.hashCode(borad);
        return result;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamcolor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamcolor) {
        if (!isInCheck(teamcolor)) {
            return false;
        }// check if the piece can be blocked
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                if (currentPiece!= null && currentPiece.getTeamColor() == teamcolor) {
                    Collection<ChessMove> pieceMoves = currentPiece.pieceMoves(board, currentPosition);
                    for (ChessMove move : pieceMoves) {
                        if (!isInCheck(teamcolor)) {
                            return false;
                        }
                    }
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
        if (!isInCheck(teamColor)) {
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    ChessPosition currentPosition = new ChessPosition(row, col);
                    ChessPiece currentPiece = board.getPiece(currentPosition);
                    if (currentPiece != null && currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() != ChessPiece.PieceType.KING) {
                        if (validMoves(currentPosition) != null) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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
