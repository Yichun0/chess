package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.Test;
import server.RequestResponses.ErrorResponse;
import server.RequestResponses.RegisterRequest;
import server.RequestResponses.RegisterResult;
import Service.RegisterServices;

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
        try {
            RegisterRequest request = new RegisterRequest("username", "password", null);
            RegisterResult result = services.registerUser(request);
            assertNotNull(result.authToken());
        } catch (DataAccessException exception) {
            assertEquals(new ErrorResponse("Error: bad request"), new ErrorResponse(exception.getMessage()),"Error: bad request");
        }
    }
}

