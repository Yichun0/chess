package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.GameData;
import org.junit.jupiter.api.Test;
import server.Response.ErrorResponse;
import server.Requests.JoinGameRequest;
import Service.JoinGameServices;

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
        joinGameService.joinGame(joinGameRequest, authData.getAuthToken());
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
            joinGameService.joinGame(joinGameRequest, "wrongAuthToken");
        } catch (DataAccessException exception){
            assertEquals(new ErrorResponse("Error: unauthorized"), new ErrorResponse(exception.getMessage()));
        }
    }

}
