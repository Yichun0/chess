package server.Services;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import server.RequestResponses.LogoutRequest;

public class LogoutServices {
    public LogoutServices logoutServices(LogoutRequest logoutRequest) throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        String authToken = logoutRequest.getAuthToken();
        AuthData authData = new AuthData(null,authToken);
        if(!authDAO.readAuthToken(authData)|| authToken == null){
            throw new DataAccessException("Error: unauthorized");
        }
        else if (authDAO.readAuthToken(authData)){
            authDAO.deleteAuthtoken(authData);
        }
        throw new DataAccessException("Error: description");
    }
}
