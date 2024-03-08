package dataAccessTests;

import Model.GameData;
import Model.UserData;
import Service.LoginServices;
import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Requests.JoinGameRequest;
import server.Requests.LoginRequest;
import server.Response.ErrorResponse;
import server.Response.LoginRespond;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameDAOTests {
    GameDAO game = new SQLGameDAO();
    @BeforeEach
    public void clear() throws DataAccessException {
        game.clearGameDAO();
    }

    @Test
    public void positiveClearTest() throws DataAccessException {
        GameData newgame = new GameData(1, null,null, "gameName",null);
        game.createGame(newgame);
        game.clearGameDAO();;
        assertFalse(game.findGame("gameName",1));

    }

    @Test
    public void createPositive() throws DataAccessException{
        GameData newgame = new GameData(1, null,null, "gameName",null);
        game.createGame(newgame);
        assertTrue(game.findGame("gameName",1));
    }

    @Test
    public void createNegative() throws DataAccessException{
        GameData newgame = new GameData(1, null,null, "gameName",null);
        game.createGame(newgame);
        try{
            game.createGame(newgame);
        } catch (DataAccessException exception){
            assertEquals(new ErrorResponse("Error: already taken"), new ErrorResponse(exception.getMessage()), "Error: unauthorized");
        }
    }

    @Test
    public void findPositive() throws DataAccessException{
        GameData newgame = new GameData(1, null,null, "gameName",null);
        game.createGame(newgame);
        assertTrue(game.findGame("gameName",1));
    }
    @Test
    public void findNegative() throws DataAccessException{
        GameData newgame = new GameData(1, null,null, "gameName",null);
        game.createGame(newgame);
        assertFalse(game.findGame("wrongID",2));
    }
    @Test
    public void joinPositive() throws DataAccessException, SQLException {
        GameData newgame = new GameData(1, null, null, "gameName", null);
        game.createGame(newgame);
        JoinGameRequest request = new JoinGameRequest("White", 1);
        String username = "username";
        game.joinGame(username, request);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT whiteUsername FROM gametable WHERE gameID = ?")) {
            preparedStatement.setInt(1, 1);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String whiteUsername = resultSet.getString("whiteUsername");
                    assertEquals("username", whiteUsername);
                }
            }
        }
    }


    @Test
    public void joinNegative() throws DataAccessException,SQLException{
        GameData newgame = new GameData(1, null,null, "gameName",null);
        game.createGame(newgame);
        JoinGameRequest request = new JoinGameRequest("White",0);
        String username = "username";
        try{
            game.joinGame(username,request);
        } catch (DataAccessException e){
            assertEquals(new ErrorResponse("Error: bad request"), new ErrorResponse(e.getMessage()), "Error: unauthorized");
        }
    }

    @Test
    public void listPositive() throws DataAccessException{
        GameData newgame1 = new GameData(1, null,null, "gameName",null);
        GameData newgame2  = new GameData(2, null,null,"game2",null);
        game.createGame(newgame1);
        game.createGame(newgame2);
        assertEquals(2,game.listGame().size());
    }

}
