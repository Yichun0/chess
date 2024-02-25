package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;

public interface AuthDAO {
    boolean readAuthToken(AuthData authObjects) throws DataAccessException;
    void deleteAuthtoken(AuthData authObjects) throws DataAccessException;
    void createAuthToken(AuthData authObjets) throws DataAccessException;
    void clearAuthToken() throws DataAccessException;
}
