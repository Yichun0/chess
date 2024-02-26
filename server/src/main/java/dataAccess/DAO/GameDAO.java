package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.Model.GameData;
import server.RequestResponses.CreateGameRespond;

public interface GameDAO {
    void clearGameDAO() throws DataAccessException;

    void createGame(GameData gameData) throws DataAccessException;

    boolean findGame(String gameName) throws DataAccessException;
}
