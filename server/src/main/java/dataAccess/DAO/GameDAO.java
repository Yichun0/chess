package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clearGameDAO() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;

    void createGame(GameData gameData) throws DataAccessException;

    boolean findGame(String gameName, int gameID) throws DataAccessException;
    Collection<GameData> listGame();
}
