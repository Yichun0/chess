package dataAccess.DAO;

import Model.AuthData;
import exception.DataAccessException;
import dataAccess.DatabaseManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class SQLAuthDao implements AuthDAO{
    public static Map<String, AuthData> AuthTokens = new HashMap<>();
    // the String actual authToken is the key --> map to the authData object


    public void clearAuthToken() throws DataAccessException{
        String statement = "TRUNCATE authTable";
        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void createAuthToken(AuthData authObjets) throws DataAccessException{
        var statement = "INSERT INTO authTable (authtoken, username) VALUES (?,?) ";
        try (Connection connection = DatabaseManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(statement)){
            preparedStatement.setString(1, authObjets.getAuthToken());
            preparedStatement.setString(2, authObjets.getUsername());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }

    }

    @Override
    public boolean findAuthToken(AuthData authObjects) throws DataAccessException {
        String sql = "SELECT * FROM authTable WHERE authToken = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, authObjects.getAuthToken());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
//            return false;
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    public void deleteAuthtoken(AuthData authObjects) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM authTable WHERE authToken = ?")) {
            preparedStatement.setString(1,authObjects.getAuthToken());
            int effect = preparedStatement.executeUpdate();
            if (effect == 0){
                throw new DataAccessException("Error: unauthorized");
            }
        }

        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }
    public String getUsername(AuthData authData) throws DataAccessException{
        if (!findAuthToken(authData)){
            throw new DataAccessException("Error: unauthorized");
        }
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT username FROM authTable WHERE authToken = ?")) {
            preparedStatement.setString(1, authData.getAuthToken());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                }
                return null;
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
