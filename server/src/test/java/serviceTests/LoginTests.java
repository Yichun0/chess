package serviceTests;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import Model.UserData;
import org.junit.jupiter.api.Test;
import server.Requests.*;
import Service.LoginServices;
import server.Response.ErrorResponse;
import server.Response.LoginRespond;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests {
    @Test
    public void positiveTest() throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        UserData newUser = new UserData("username", "password", "email");
        userDAO.createUser(newUser);
        userDAO.createPassword(newUser);
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
