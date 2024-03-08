package dataAccessTests;

import Model.AuthData;
import Model.UserData;
import Service.LoginServices;
import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.Requests.LoginRequest;
import server.Response.ErrorResponse;
import server.Response.LoginRespond;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTests {
    SQLUserDAO user = new SQLUserDAO();
    @BeforeEach
    public void clear() throws DataAccessException {
        user.clearUserDAO();
    }

    @Test
    public void positiveClearTest() throws DataAccessException {
        UserData userData = new UserData("username","password", "email");
        boolean rs = user.verifyUser(userData);
        assertEquals("false", String.valueOf(rs));

    }
    @Test
    public void createPositive() throws DataAccessException {
        String password = "password";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        UserData userData = new UserData("username",hashedPassword, "email");
        user.createUser(userData);
        boolean rs = user.verifyUser(new UserData("username","password","email"));
        assertTrue(rs, "String.valueOf(rs)");
    }

    @Test
    public void createNegative() throws DataAccessException{
        UserData userData = new UserData("username","password", "email");
        user.createUser(userData);
        try{
            user.createUser(userData);
        } catch (DataAccessException e){
            assertEquals(new ErrorResponse("Error: already taken"), new ErrorResponse(e.getMessage()), "Error: unauthorized");
        }
    }
    @Test
    public void verifyPositive() throws DataAccessException{
        UserData userData = new UserData("username","password", "email");
        boolean rs = user.verifyUser(userData);
        assertEquals("false", String.valueOf(rs));
    }

    @Test
    public void verifyNegative() throws DataAccessException {
        UserData userData = new UserData("username", "password", "email");
        user.createUser(userData);
        boolean rs = user.verifyUser(new UserData("username", "password", "email"));
        assertFalse(rs, "wrong ");
    }
}
