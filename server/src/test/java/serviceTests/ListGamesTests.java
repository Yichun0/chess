package serviceTests;

import dataAccess.DAO.*;
import Service.ClearServices;
import exception.DataAccessException;
import Model.AuthData;
import Model.GameData;
import org.junit.jupiter.api.Test;
import Requests.CreateGameRequest;
import Response.ErrorResponse;
import Service.CreateGameServices;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListGamesTests {
    @Test
    public void postitiveTest() throws DataAccessException, SQLException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new SQLAuthDao();
        GameDAO gameDAO = new MemoryGameDAO();
        ClearServices clear = new ClearServices();
        clear.clearData();
        AuthData authToken = new AuthData("username", "testAuth");
        authDAO.createAuthToken(authToken);
        GameData game1 = new GameData(1234,null,null,"Game",null);
        GameData game2 = new GameData(2234,null,null,"Game2",null);
        gameDAO.createGame(game1);
        gameDAO.createGame(game2);
        assertEquals(2, gameDAO.listGame().size());
    }
    @Test
    public void negatitiveTest() throws DataAccessException{
        AuthDAO testAuthDAO = new SQLAuthDao();
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
