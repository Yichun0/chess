package dataAccess.DAO;

import Model.AuthData;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class SQLAuthDao implements AuthDAO{
    public static Map<String, AuthData> AuthTokens = new HashMap<>();
    // the String actual authToken is the key --> map to the authData object
    private final String[] createTables = {
            """
            CREATE TABLE IF NOT EXISTS userTable (
                          `username` varchar(256) NOT NULL,
                          `password` int NOT NULL,
                          `email` varchar(256) NOT NULL,
                          PRIMARY KEY (`username`),
                        )
            """
    };

    public SQLAuthDao() throws DataAccessException{
        configureDatabase();
    }
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createTables) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void clearAuthToken() throws DataAccessException {
        AuthTokens.clear();
    }

    public void createAuthToken(AuthData authObjets) throws DataAccessException{
        var statement = "INSERT INTO auth(authtoken, username) VALUES (?,?,?) ";
        AuthTokens.put(authObjets.getAuthToken(), authObjets);

    }

    @Override
    public boolean findAuthToken(AuthData authObjects) throws DataAccessException {
        return AuthTokens.containsKey(authObjects.getAuthToken());
    }

    public void deleteAuthtoken(AuthData authObjects) throws DataAccessException{
        if (!findAuthToken(authObjects)){
            throw new DataAccessException("Error: unauthorized");
        }
        AuthTokens.remove(authObjects.getAuthToken());
    }
    public String getUsername(AuthData authData) throws DataAccessException{
        if (!findAuthToken(authData)){
            throw new DataAccessException("Error: unauthorized");
        }
        return AuthTokens.get(authData.getAuthToken()).getUsername();
    }
}
