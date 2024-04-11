package Service;

import dataAccess.DAO.*;
import exception.DataAccessException;
import Model.AuthData;
import Model.GameData;
import Response.ListGamesRespond;

import java.sql.SQLException;
import java.util.Collection;

public class ListGamesServices {
    public ListGamesRespond listGames(String authToken) throws DataAccessException, SQLException {
        GameDAO gameDAO = new SQLGameDAO();
        AuthDAO authDAO = new SQLAuthDao();
        if (!authDAO.findAuthToken(authToken)){
            throw new DataAccessException("Error: unauthorized");
        }
        else {
            Collection<GameData> games = gameDAO.listGame();
            return new ListGamesRespond(games);
        }
    }
}