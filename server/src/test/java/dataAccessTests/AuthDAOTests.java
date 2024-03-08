package dataAccessTests;

import Model.AuthData;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.SQLAuthDao;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Response.ErrorResponse;

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
    public void create_negative() throws DataAccessException {
        AuthData authData = new AuthData(null, "authToken");
        authDAO.createAuthToken(authData);

    }
}
