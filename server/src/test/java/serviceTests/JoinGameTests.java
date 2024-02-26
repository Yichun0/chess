package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import dataAccess.Model.GameData;
import org.junit.jupiter.api.Test;
import server.RequestResponses.ErrorResponse;
import server.RequestResponses.JoinGameRequest;
import server.Services.JoinGameServices;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinGameTests {
    @Test
    public void positiveTest() throws DataAccessException {
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        AuthData authData = new AuthData("username","authToken");
        authDAO.createAuthToken(authData);
        JoinGameServices joinGameService = new JoinGameServices();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);
        GameData newGame = new GameData(1, null, null, "game", null);
        gameDAO.createGame(newGame);
        joinGameService.JoinGame(joinGameRequest, authData.getAuthToken());
        assertEquals("username",authDAO.getUsername(authData));

    }
    @Test
    public void negativeTest() throws DataAccessException{
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        AuthData authData = new AuthData("username","authToken");
        authDAO.createAuthToken(authData);
        JoinGameServices joinGameService = new JoinGameServices();
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);
        GameData newGame = new GameData(1, null, null, "game", null);
        gameDAO.createGame(newGame);
        try{
            joinGameService.JoinGame(joinGameRequest, "wrongAuthToken");
        } catch (DataAccessException exception){
            assertEquals(new ErrorResponse("Error: unauthorized"), new ErrorResponse(exception.getMessage()));
        }
    }

}
