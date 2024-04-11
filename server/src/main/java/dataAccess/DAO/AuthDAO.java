package dataAccess.DAO;

import exception.DataAccessException;
import Model.AuthData;

import java.sql.SQLException;

public interface AuthDAO {
    boolean findAuthToken(String authToken) throws DataAccessException, SQLException;
    void deleteAuthtoken(AuthData authObjects) throws DataAccessException;
    String getUsername(AuthData authObjects) throws DataAccessException, SQLException;
    void createAuthToken(AuthData authObjets) throws DataAccessException;
    void clearAuthToken() throws DataAccessException, SQLException;
}
