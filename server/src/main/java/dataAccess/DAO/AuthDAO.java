package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;

public interface AuthDAO {
    boolean findAuthToken(AuthData authObjects) throws DataAccessException;
    void deleteAuthtoken(AuthData authObjects) throws DataAccessException;
    String getUsername(AuthData authData) throws DataAccessException;
    void createAuthToken(AuthData authObjets) throws DataAccessException;
    void clearAuthToken() throws DataAccessException;
}
