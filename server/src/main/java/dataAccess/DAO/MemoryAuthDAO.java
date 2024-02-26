package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    // used the model class
    public static Map<String, AuthData> AuthTokens = new HashMap<>();
    // the String actual authToken is the key --> map to the authData object

    public void clearAuthToken() throws DataAccessException {
        AuthTokens.clear();
    }

    public void createAuthToken(AuthData authObjets) throws DataAccessException{
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


    public static void setAuthTokens(Map<String, AuthData> authTokens) {
        MemoryAuthDAO.AuthTokens = authTokens;
    }
}
