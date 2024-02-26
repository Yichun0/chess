package Service;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.GameData;
import server.Response.ListGamesRespond;

import java.util.Collection;

public class ListGamesServices {
    public ListGamesRespond listGames(String authToken) throws DataAccessException {
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        AuthData authData = new AuthData(null, authToken);
        if (!authDAO.findAuthToken(authData)){
            throw new DataAccessException("Error: unauthorized");
        }
        else {
            Collection<GameData> games = gameDAO.listGame();
            return new ListGamesRespond(games);
        }
    }
}
