package server.Services;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import server.RequestResponses.LogoutRequest;

public class LogoutServices {
    public void logoutServices(AuthData authData) throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        if(!authDAO.findAuthToken(authData)){
            throw new DataAccessException("Error: unauthorized");
        }
        else{
            authDAO.deleteAuthtoken(authData);
        }
    }
}
