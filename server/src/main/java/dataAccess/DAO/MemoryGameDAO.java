package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{
    public static Map<Integer, GameData> gameDatas = new HashMap<>();
    public void clearGameDAO() throws DataAccessException {
        gameDatas.clear();
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException {
       gameDatas.put(gameData.getGameID(), gameData);
    }

    public boolean findGame(String gameName, int gameID) throws DataAccessException{
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
