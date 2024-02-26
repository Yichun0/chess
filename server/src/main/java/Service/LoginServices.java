package Service;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.UserData;
import server.Requests.LoginRequest;
import server.Response.LoginRespond;

import java.util.UUID;

public class LoginServices {
    public LoginRespond loginUser(LoginRequest loginRequest) throws DataAccessException {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO =  new MemoryAuthDAO();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        UserData userData = new UserData(username,password,null);
        if (username == null || password == null|| !userDAO.findUser(userData) || !userDAO.findPassword(userData)){
            throw new DataAccessException("Error: unauthorized");
        } else if (userDAO.findUser(userData)) {
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData(username, authToken);
            authDAO.createAuthToken(authData);
            return new LoginRespond(username,authToken);
        }
        throw new DataAccessException("Error: description");
    }
}
