package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.Model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{
    public static Map<String, GameData> gameDatas = new HashMap<>();

    public void clearGameDAO() throws DataAccessException {
        gameDatas.clear();
    }
}
