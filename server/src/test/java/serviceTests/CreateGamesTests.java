package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.RequestResponses.CreateGameRequest;
import server.RequestResponses.CreateGameRespond;
import server.RequestResponses.ErrorResponse;
import server.Services.CreateGameServices;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateGamesTests {
    @Test
    public void positiveTest() throws DataAccessException {
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        CreateGameServices createGameService = new CreateGameServices();
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        CreateGameRequest request = new CreateGameRequest("gameName","authToken");
        CreateGameRespond respond = createGameService.createGame(request,authData);
        assertEquals(true, gameDAO.findGame("gameName",respond.getGameID()));
    }
    @Test
    public void NegativeTest() throws DataAccessException{
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        CreateGameServices createGameService = new CreateGameServices();
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        CreateGameRequest request = new CreateGameRequest("gameName","WrongAuthToken");
        try{
            createGameService.createGame(request,authData);
        } catch (DataAccessException exception){
            assertEquals(new ErrorResponse("Error: unauthorized"), new ErrorResponse(exception.getMessage()), "Error: unauthorized");
        }
    }
}
