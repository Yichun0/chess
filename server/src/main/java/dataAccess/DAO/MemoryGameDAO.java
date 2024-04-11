package dataAccess.DAO;

import chess.ChessGame;
import exception.DataAccessException;
import Model.GameData;
import Requests.JoinGameRequest;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{
    public static Map<Integer, GameData> gameDatas = new HashMap<>();
    public void clearGameDAO(){
        gameDatas.clear();
    }

    @Override
    public void deleteUser(String playerColor, int gameID) {

    }

    @Override
    public void updateGame(ChessGame newGame, int gameID) throws DataAccessException {

    }

    @Override
    public void deleteGameID(Integer gameID) throws DataAccessException {

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public boolean playerTaken(int gameID, String playerColor, String username) {
        return false;
    }

    @Override
    public int getGameID(String gameName) throws DataAccessException, SQLException {
        return 0;
    }

    @Override
    public int createGame(GameData gameData) throws DataAccessException {
        int gameID = gameDatas.size();
       gameDatas.put(gameID,gameData);
       return gameID;
    }

    public boolean findGame(int gameID){
        return gameDatas.containsKey(gameID);
    }

    public void joinGame(String username, JoinGameRequest request ) throws DataAccessException{

    }

    public Collection<GameData> listGame(){
        return gameDatas.values();
    }
}
