package Service;

import chess.ChessGame;
import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.GameData;
import server.Requests.CreateGameRequest;
import server.Response.CreateGameRespond;

public class CreateGameServices {
    public CreateGameRespond createGame(CreateGameRequest gameRequest, AuthData authToken) throws DataAccessException {
        String gameName = gameRequest.getGameName();
        GameDAO gameDAO = new SQLGameDAO();
        AuthDAO authDAO = new SQLAuthDao();
        if (gameName == null){
            throw new DataAccessException("Error: bad request");
        } else if (!authDAO.findAuthToken(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
        else {

            CreateGameRespond gameID = gameDAO.createGame(gameName);
            return gameID;
        }
    }
}
