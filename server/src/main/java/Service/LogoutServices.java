package Service;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DataAccessException;
import Model.AuthData;

public class LogoutServices {
    public void logoutServices(AuthData authData) throws DataAccessException {
        AuthDAO authDAO = new MemoryAuthDAO();
        try{
            authDAO.deleteAuthtoken(authData);
        } catch (DataAccessException e){
            throw e;
        }
    }
}
