package dataAccessTests;

import Model.UserData;
import Service.LoginServices;
import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Requests.LoginRequest;
import server.Response.ErrorResponse;
import server.Response.LoginRespond;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDAOTests {
    UserDAO userDAO = new SQLUserDAO();
    @BeforeEach
    public void clear() throws DataAccessException {
        userDAO.clearUserDAO();
    }
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
        UserDAO userDAO = new SQLUserDAO();
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
