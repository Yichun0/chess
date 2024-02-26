package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import Model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.RequestResponses.LogoutRequest;
import Service.LogoutServices;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTests {
    @Test
    public void positiveTest() throws DataAccessException {
        AuthDAO testAuthDAO = new MemoryAuthDAO();
        AuthData authToken = new AuthData("username", "testAuth");
        testAuthDAO.createAuthToken(authToken);
        LogoutRequest logoutRequest = new LogoutRequest(authToken.getAuthToken());
        LogoutServices logoutTest = new LogoutServices();
        logoutTest.logoutServices(authToken);
        assertEquals(false,testAuthDAO.findAuthToken(authToken),"success");
    }
    @Test
    public void negativeTest() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        AuthData authToken = new AuthData("username", "testAuth");
        authDAO.createAuthToken(authToken);
        LogoutServices services = new LogoutServices();
        services.logoutServices(authToken);
        Assertions.assertThrows(DataAccessException.class, ()-> services.logoutServices(authToken),"Error: unauthorized");
    }
}
