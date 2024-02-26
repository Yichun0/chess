package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import dataAccess.Model.AuthData;
import org.junit.jupiter.api.Test;
import server.Handlers.ClearHandler;
import server.RequestResponses.ClearResponse;
import server.RequestResponses.RegisterRequest;
import server.RequestResponses.RegisterResult;
import server.Services.ClearServices;
import server.Services.RegisterServices;

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
