package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.GameData;
import server.Response.CreateGameRespond;

import java.util.Collection;

public interface GameDAO {
    void clearGameDAO() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;

    int createGame(GameData gameData) throws DataAccessException;

    boolean findGame(String gameName, int gameID) throws DataAccessException;
    Collection<GameData> listGame() throws DataAccessException;
}
