package Service;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.SQLAuthDao;
import dataAccess.DataAccessException;
import Model.AuthData;

public class LogoutServices {
    public void logoutServices(AuthData authData) throws DataAccessException {
        AuthDAO authDAO = new SQLAuthDao();
        try{
            authDAO.deleteAuthtoken(authData);
        } catch (DataAccessException e){
            throw e;
        }
    }
}
