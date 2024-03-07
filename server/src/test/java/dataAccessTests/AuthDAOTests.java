package dataAccessTests;

import Model.AuthData;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.SQLAuthDao;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AuthDAOTests {
    static void insertUser() throws DataAccessException {
        String username = "username";
        String authToken = "authToken";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO authTable (authtoken, username) VALUES (?,?)")) {
            preparedStatement.setString(1, authToken);
            preparedStatement.setString(2, username);

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Test
    public void positiveClearTest() throws DataAccessException, SQLException {
        AuthDAO authDAO = new SQLAuthDao();
        authDAO.clearAuthToken();
        AuthData authData = new AuthData("username", "authToken");
        assertFalse(authDAO.findAuthToken(authData));

    }

    @Test
    public void positiveCreateTest() throws DataAccessException, SQLException {
        AuthDAO authDAO = new SQLAuthDao();
        authDAO.clearAuthToken();
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT username FROM authTable WHERE authToken = ?")) {
            preparedStatement.setString(1, authData.getAuthToken());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    assertEquals("username", username);
                }
            }
        }
    }
}
