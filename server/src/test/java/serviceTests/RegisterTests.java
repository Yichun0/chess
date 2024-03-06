package serviceTests;

import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.Test;
import server.Response.ErrorResponse;
import server.Requests.RegisterRequest;
import server.Response.RegisterResult;
import Service.RegisterServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RegisterTests {
    @Test
    public void positiveTest() throws Exception {
        UserDAO userDAO = new SQLUserDAO();
        AuthDAO authDAO = new SQLAuthDao();
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

