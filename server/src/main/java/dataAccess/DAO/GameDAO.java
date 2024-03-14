package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.GameData;
import Requests.JoinGameRequest;

import java.sql.SQLException;
import java.util.Collection;

public interface GameDAO {
    void clearGameDAO() throws DataAccessException;
    void joinGame(String username, JoinGameRequest request ) throws DataAccessException, SQLException;

    int createGame(GameData gameData) throws DataAccessException;

    boolean findGame(String gameName, int gameID) throws DataAccessException;
    Collection<GameData> listGame() throws DataAccessException;
}
