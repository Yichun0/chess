package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import exception.DataAccessException;
import Model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Requests.LogoutRequest;
import Service.LogoutServices;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTests {
    @Test
    public void positiveTest() throws DataAccessException, SQLException {
        AuthDAO testAuthDAO = new MemoryAuthDAO();
        AuthData authToken = new AuthData("username", "testAuth");
        testAuthDAO.createAuthToken(authToken);
        LogoutServices logoutTest = new LogoutServices();
        logoutTest.logoutServices(authToken);
        assertEquals(false,testAuthDAO.findAuthToken("testAuth"),"success");
    }
    @Test
    public void negativeTest() throws DataAccessException, SQLException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        AuthData authToken = new AuthData("username", "testAuth");
        authDAO.createAuthToken(authToken);
        LogoutServices services = new LogoutServices();
        services.logoutServices(authToken);
        Assertions.assertThrows(DataAccessException.class, ()-> services.logoutServices(authToken),"Error: unauthorized");
    }
}
