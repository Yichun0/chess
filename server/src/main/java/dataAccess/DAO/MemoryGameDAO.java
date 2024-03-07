package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.GameData;
import server.Response.CreateGameRespond;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{
    public static Map<Integer, GameData> gameDatas = new HashMap<>();
    public void clearGameDAO(){
        gameDatas.clear();
    }

    @Override
    public int createGame(GameData gameData) throws DataAccessException {
        int gameID = gameDatas.size();
       gameDatas.put(gameID,gameData);
       return gameID;
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
