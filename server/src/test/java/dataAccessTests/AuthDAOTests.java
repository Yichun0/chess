package dataAccessTests;

import Model.AuthData;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.SQLAuthDao;
import exception.DataAccessException;
import dataAccess.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Response.ErrorResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDAOTests {
    AuthDAO authDAO = new SQLAuthDao();

    @BeforeEach
    public void clear() throws DataAccessException, SQLException {
        authDAO.clearAuthToken();
    }

    @Test
    public void positiveClearTest() throws DataAccessException, SQLException {
        authDAO.clearAuthToken();
        AuthData authData = new AuthData("username", "authToken");
        assertFalse(authDAO.findAuthToken(authData));

    }

    @Test
    public void positiveCreateTest() throws DataAccessException, SQLException {
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT username FROM authTable WHERE authToken = ?")) {
            preparedStatement.setString(1, authData.getAuthToken());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    assertEquals("username", username);
                } else {
                    fail();
                }
            }
        }
    }
    @Test
    public void createNegative() throws DataAccessException {
        AuthData authData = new AuthData(null, "authToken");
        try {
            authDAO.createAuthToken(authData);
        } catch (DataAccessException e) {
            assertEquals(new ErrorResponse("Column 'username' cannot be null"), new ErrorResponse(e.getMessage()), "Error: unauthorized");
        }
    }
    @Test
    public void findPositive() throws DataAccessException{
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        assertEquals(true, authDAO.findAuthToken(authData));

    }
    @Test
    public void findNegative() throws DataAccessException {
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        AuthData wrongData = new AuthData("wrongName", "wrongAuthToken");
        assertEquals(false, authDAO.findAuthToken(wrongData));
    }
    @Test
    public void deletePositive() throws DataAccessException{
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        authDAO.deleteAuthtoken(authData);
        assertEquals(false, authDAO.findAuthToken(authData));
    }

    @Test
    public void deleteNegative() throws DataAccessException {
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        AuthData wrongAuthdata = new AuthData("username", "wrongAutoken");
        try {
            authDAO.deleteAuthtoken(wrongAuthdata);
        } catch (DataAccessException exception) {
            assertEquals(new ErrorResponse("Error: unauthorized"), new ErrorResponse(exception.getMessage()), "Error: unauthorized");
        }
    }
    @Test
    public void getPositive() throws DataAccessException{
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        String username = authDAO.getUsername(authData);
        assertEquals("username", username);
    }

    @Test
    public void getNegative() throws DataAccessException{
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        AuthData wrongAuthdata = new AuthData("username", "wrongAutoken");
        try {
            authDAO.getUsername(wrongAuthdata);
        } catch (DataAccessException exception) {
            assertEquals(new ErrorResponse("Error: unauthorized"), new ErrorResponse(exception.getMessage()), "Error: unauthorized");
        }
    }
}
