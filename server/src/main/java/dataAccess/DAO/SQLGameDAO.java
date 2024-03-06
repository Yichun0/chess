package dataAccess.DAO;

import Model.GameData;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SQLGameDAO implements GameDAO{
    public static Map<Integer, GameData> gameDatas = new HashMap<>();

    public void clearGameDAO(){
        gameDatas.clear();
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {
        gameDatas.put(gameData.getGameID(), gameData);
    }

    public boolean findGame(String gameName, int gameID){
        GameData game = new GameData(gameID,null, null, gameName, null);
        return gameDatas.containsKey(gameID);
    }

    public GameData getGame(int gameID) throws DataAccessException{
        GameData game = gameDatas.get(gameID);
        if (game == null){
            throw new DataAccessException("Error: bad request");
        }
        return game;
    }
    public Collection<GameData> listGame(){
        return gameDatas.values();
    }
}
