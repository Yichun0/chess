package serviceTests;

import Requests.LoginRequest;
import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import Model.UserData;
import org.junit.jupiter.api.Test;
import Service.LoginServices;
import Response.ErrorResponse;
import Response.LoginRespond;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests {
    @Test
    public void positiveTest() throws DataAccessException {
        UserDAO userDAO = new SQLUserDAO();
        UserData newUser = new UserData("username", "password", "email");
        userDAO.createUser(newUser);
        LoginServices loginService = new LoginServices();
        LoginRequest request = new LoginRequest("username", "password");
        LoginRespond result = loginService.loginUser(request);
        assertEquals("username", result.getUsername());
    }

    @Test
    public void negativeTest() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        LoginServices services = new LoginServices();
        try {
            userDAO.createUser(new UserData("username", "password", "email"));
            LoginRequest request = new LoginRequest("username", null );
            LoginRespond result = services.loginUser(request);
            assertNotNull(result.getAuthToken());
        } catch (DataAccessException exception) {
            assertEquals(new ErrorResponse("Error: unauthorized"), new ErrorResponse(exception.getMessage()),"Error: unauthorized");
        }
    }
}
