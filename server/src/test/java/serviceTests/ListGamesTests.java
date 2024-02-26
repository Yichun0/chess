package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import dataAccess.Model.GameData;
import org.junit.jupiter.api.Test;
import server.RequestResponses.CreateGameRequest;
import server.RequestResponses.CreateGameRespond;
import server.RequestResponses.ErrorResponse;
import server.Services.CreateGameServices;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListGamesTests {
    @Test
    public void postitiveTest() throws DataAccessException {
        AuthDAO testAuthDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        CreateGameServices createGameService = new CreateGameServices();
        AuthData authToken = new AuthData("username", "testAuth");
        testAuthDAO.createAuthToken(authToken);
        GameData game1 = new GameData(1234,null,null,"Game",null);
        GameData game2 = new GameData(2234,null,null,"Game2",null);
        gameDAO.createGame(game1);
        gameDAO.createGame(game2);
        assertEquals(2, gameDAO.listGame().size());
    }
    @Test
    public void negatitiveTest() throws DataAccessException{
        AuthDAO testAuthDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        CreateGameServices createGameService = new CreateGameServices();
        AuthData authToken = new AuthData("username", "testAuth");
        testAuthDAO.createAuthToken(authToken);
        GameData game1 = new GameData(1234,null,null,"Game",null);
        CreateGameRequest gameRequest = new CreateGameRequest(null,"wrongAuthToken");
        try{
            createGameService.createGame(gameRequest,authToken);
        }
        catch (DataAccessException exception){
            assertEquals(new ErrorResponse("Error: bad request"), new ErrorResponse(exception.getMessage()),"Error: bad request");
        }
    }
}
