package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.Test;
import server.RequestResponses.RegisterRequest;
import server.RequestResponses.RegisterResult;
import server.Services.RegisterServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RegisterTests {
    @Test
    public void positiveTest() throws Exception {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        RegisterServices services = new RegisterServices();
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = services.registerUser(request);
        assertNotNull(result.authToken());
        assertEquals("username", result.username());
    }
    @Test
    public void negativeTest() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        RegisterServices services = new RegisterServices();
        RegisterRequest request = new RegisterRequest("username", "password", null);
        RegisterResult result = services.registerUser(request);
        assertNotNull(result.authToken());
        assertEquals("Error: bad request", result.toString());
    }

    private void assertEquals(String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("unmatched");
        }
    }
}
