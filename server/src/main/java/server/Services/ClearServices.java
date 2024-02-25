package server.Services;

import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import server.RequestResponses.ClearResponse;

public class ClearServices {

   public void clearData() throws DataAccessException{
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        userDAO.clearUserDAO();
        gameDAO.clearGameDAO();
        authDAO.clearAuthToken();
   }
}
