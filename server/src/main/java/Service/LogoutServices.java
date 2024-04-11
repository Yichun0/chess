package Service;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.SQLAuthDao;
import exception.DataAccessException;
import Model.AuthData;

import java.sql.SQLException;

public class LogoutServices {
    public void logoutServices(AuthData authData) throws DataAccessException, SQLException {
        AuthDAO authDAO = new SQLAuthDao();
        if (!authDAO.findAuthToken(authData.getAuthToken())){
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
