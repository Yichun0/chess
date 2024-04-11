package Service;

import dataAccess.DAO.*;
import exception.DataAccessException;
import Model.AuthData;
import Model.GameData;
import Requests.CreateGameRequest;
import Response.CreateGameRespond;

import java.sql.SQLException;

public class CreateGameServices {
    public CreateGameRespond createGame(CreateGameRequest gameRequest, AuthData authToken) throws DataAccessException, SQLException {
        String gameName = gameRequest.getGameName();
        GameDAO gameDAO = new SQLGameDAO();
        AuthDAO authDAO = new SQLAuthDao();
        if (gameName == null){
            throw new DataAccessException("Error: bad request");
        } else if (!authDAO.findAuthToken(authToken.getAuthToken())) {
            throw new DataAccessException("Error: unauthorized");
        }
        else {
            GameData gameData = new GameData(0,null,null,gameName,null);
            int gameID = gameDAO.createGame(gameData);
            CreateGameRespond gameRespond = new CreateGameRespond(gameID);
            return gameRespond;
        }
    }
}
