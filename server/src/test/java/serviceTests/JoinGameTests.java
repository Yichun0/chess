package serviceTests;

import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.GameData;
import org.junit.jupiter.api.Test;
import Response.ErrorResponse;
import Requests.JoinGameRequest;
import Service.JoinGameServices;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinGameTests {
    @Test
    public void positiveTest() throws DataAccessException, SQLException {
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new SQLAuthDao();
        AuthData authData = new AuthData("username","authToken");
        authDAO.createAuthToken(authData);
        JoinGameServices joinGameService = new JoinGameServices();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);
        GameData newGame = new GameData(1, null, null, "game", null);
        gameDAO.createGame(newGame);
        joinGameService.joinGame(joinGameRequest, authData.getAuthToken());
        assertEquals("username",authDAO.getUsername(authData));

    }
    @Test
    public void negativeTest() throws DataAccessException{
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new SQLAuthDao();
        AuthData authData = new AuthData("username","authToken");
        authDAO.createAuthToken(authData);
        JoinGameServices joinGameService = new JoinGameServices();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);
        GameData newGame = new GameData(1, null, null, "game", null);
        gameDAO.createGame(newGame);
        try{
            joinGameService.joinGame(joinGameRequest, "wrongAuthToken");
        } catch (DataAccessException | SQLException exception){
            assertEquals(new ErrorResponse("Error: unauthorized"), new ErrorResponse(exception.getMessage()));
        }
    }

}
