package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.GameData;
import server.Requests.JoinGameRequest;
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

    public void joinGame(String username, JoinGameRequest request ) throws DataAccessException{

    }

    public Collection<GameData> listGame(){
        return gameDatas.values();
    }
}
