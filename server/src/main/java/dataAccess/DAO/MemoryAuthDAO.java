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
        if (AuthTokens.containsKey(authObjects.getAuthToken())){
            return true;
        }
        return false;
    }

    public void deleteAuthtoken(AuthData authObjects) throws DataAccessException{
            AuthTokens.remove(authObjects.getAuthToken(),authObjects);
        }


    public static void setAuthTokens(Map<String, AuthData> authTokens) {
        MemoryAuthDAO.AuthTokens = authTokens;
    }
}
