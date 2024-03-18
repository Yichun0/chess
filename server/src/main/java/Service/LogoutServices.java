package Service;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.SQLAuthDao;
import exception.DataAccessException;
import Model.AuthData;

public class LogoutServices {
    public void logoutServices(AuthData authData) throws DataAccessException {
        AuthDAO authDAO = new SQLAuthDao();
        if (!authDAO.findAuthToken(authData)){
            throw new DataAccessException("Error: unauthorized");
        }
        try{
            authDAO.deleteAuthtoken(authData);
        }
        catch (DataAccessException exception){
            throw exception;
        }
    }
}
