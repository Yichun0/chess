package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import Model.AuthData;
import org.junit.jupiter.api.Test;
import server.Handlers.ClearHandler;
import Service.ClearServices;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {
    @Test
    public void positiveTest() throws Exception {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        ClearServices services = new ClearServices();
        ClearHandler response = new ClearHandler();
        services.clearData();
        assertEquals(false, authDAO.findAuthToken(authData));
    }
}
