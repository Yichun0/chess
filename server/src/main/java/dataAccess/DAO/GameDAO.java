package dataAccess.DAO;

import chess.ChessGame;
import exception.DataAccessException;
import Model.GameData;
import Requests.JoinGameRequest;

import java.sql.SQLException;
import java.util.Collection;

public interface GameDAO {
    void clearGameDAO() throws DataAccessException;
    public String getGame(int gameID) throws DataAccessException ;
    boolean playerTaken(int gameID, String playerColor, String username);
    int getGameID(String gameName) throws DataAccessException, SQLException;
    String getGameName(int gameID) throws DataAccessException;
    void joinGame(String username, JoinGameRequest request ) throws DataAccessException, SQLException;

    int createGame(GameData gameData) throws DataAccessException;

    boolean findGame(int gameID) throws DataAccessException;
    Collection<GameData> listGame() throws DataAccessException;
}
