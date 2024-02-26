package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.Model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{
    public static Map<String, GameData> gameDatas = new HashMap<>();
    public void clearGameDAO() throws DataAccessException {
        gameDatas.clear();
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {
       gameDatas.put(gameData.getGameName(), gameData);
    }

    public boolean findGame(String gameName) throws DataAccessException{
        GameData game = new GameData(0,null, null, gameName, null);
        return gameDatas.containsKey(game.getGameName());
    }

    public Collection<GameData> listGame(){
        return gameDatas.values();
    }
}
