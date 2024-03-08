package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.GameData;
import server.Requests.JoinGameRequest;
import server.Response.CreateGameRespond;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface GameDAO {
    void clearGameDAO() throws DataAccessException;
    void joinGame(String username, JoinGameRequest request ) throws DataAccessException, SQLException;

    int createGame(GameData gameData) throws DataAccessException;

    boolean findGame(String gameName, int gameID) throws DataAccessException;
    Collection<GameData> listGame() throws DataAccessException;
}
