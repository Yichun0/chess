package dataAccess.DAO;

import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthDAO {
    // used the model class
    public static Map<String, String> AuthTokens = new HashMap<>();

    public void clearAuthToken() throws DataAccessException {
        AuthTokens.clear();
    }

    public void deleteAuthtoken(AuthData authObjects) throws DataAccessException{
        if (AuthTokens.containsKey(authObjects.getAuthToken())){
            String Username = authObjects.getUsername();
            AuthTokens.remove(authObjects.getAuthToken(),Username);
        }
    }
    public void updateAuthToken(AuthData authObjects) throws DataAccessException{
        // if authtoken is not in the map --> create a new authtoken
//        if (!AuthTokens.containsValue(authObjects.getUsername())){
//            String Authtoken = UUID.randomUUID().toString();
//            String Username = authObjects.getUsername();
//            AuthTokens.put(Authtoken, Username);
//        }
    }

    public boolean readAuthToken(AuthData authObjects) throws DataAccessException{
        if (AuthTokens.containsKey(authObjects.getAuthToken())){
            return true;
       }
        return false;
    }

    public static void setAuthTokens(Map<String, String> authTokens) {
        AuthDAO.AuthTokens = authTokens;
    }
}
