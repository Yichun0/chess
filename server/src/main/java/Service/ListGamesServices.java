package Service;

import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.GameData;
import Response.ListGamesRespond;

import java.util.Collection;

public class ListGamesServices {
    public ListGamesRespond listGames(String authToken) throws DataAccessException {
        GameDAO gameDAO = new SQLGameDAO();
        AuthDAO authDAO = new SQLAuthDao();
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