package Service;

import dataAccess.DAO.*;
import exception.DataAccessException;

import java.sql.SQLException;

public class ClearServices {

    public void clearData() throws DataAccessException, SQLException {
        AuthDAO authDAO = new SQLAuthDao();
        UserDAO userDAO = new SQLUserDAO();
        GameDAO gameDAO = new SQLGameDAO();
        userDAO.clearUserDAO();
        gameDAO.clearGameDAO();
        authDAO.clearAuthToken();
    }
}
