package server.Services;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import server.RequestResponses.LogoutRequest;

public class LogoutServices {
    public void logoutServices(LogoutRequest logoutRequest) throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        String authToken = logoutRequest.getAuthToken();
        AuthData authData = new AuthData(null,authToken);
        if(!authDAO.findAuthToken(authData)|| authToken == null){
            throw new DataAccessException("Error: unauthorized");
        }
        else if (authDAO.findAuthToken(authData)){
            authDAO.deleteAuthtoken(authData);
        }
        throw new DataAccessException("Error: description");
    }
}
