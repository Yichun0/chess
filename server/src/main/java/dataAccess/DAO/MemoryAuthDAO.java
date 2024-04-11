package dataAccess.DAO;

import exception.DataAccessException;
import Model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    // used the model class
    public static Map<String, AuthData> authTokens = new HashMap<>();
    // the String actual authToken is the key --> map to the authData object

    public void clearAuthToken() throws DataAccessException {
        authTokens.clear();
    }

    public void createAuthToken(AuthData authObjets) throws DataAccessException{
        authTokens.put(authObjets.getAuthToken(), authObjets);

    }

    @Override
    public boolean findAuthToken(String authToken) throws DataAccessException {
        return authTokens.containsKey(authToken);
    }

    public void deleteAuthtoken(AuthData authObjects) throws DataAccessException{
        if (!findAuthToken(authObjects.getAuthToken())){
            throw new DataAccessException("Error: unauthorized");
        }
            authTokens.remove(authObjects.getAuthToken());
        }
    public String getUsername(AuthData authData) throws DataAccessException{
        if (!findAuthToken(authData.getAuthToken())){
            throw new DataAccessException("Error: unauthorized");
        }
            return authTokens.get(authData.getAuthToken()).getUsername();
        }
}
