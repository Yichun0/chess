package Service;

import dataAccess.DAO.*;
import exception.DataAccessException;
import Model.AuthData;
import Requests.JoinGameRequest;

import java.sql.SQLException;

public class JoinGameServices {
    public void joinGame(JoinGameRequest joinRequest, String authToken) throws DataAccessException, SQLException {
        GameDAO gameDAO = new SQLGameDAO();
        AuthDAO authDAO = new SQLAuthDao();
        AuthData authData = new AuthData(null, authToken);
        String username = authDAO.getUsername(authData);
        if(joinRequest.getGameID() == null){
            throw new DataAccessException("Error: bad request");
        }
        else if (!authDAO.findAuthToken(authData)) {
            throw new DataAccessException("Error: Unauthorized");
        }
        else{
            gameDAO.joinGame(username,joinRequest);
        }
    }
}
