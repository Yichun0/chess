package Service;

import dataAccess.DAO.*;
import exception.DataAccessException;
import Model.AuthData;
import Model.UserData;
import Requests.LoginRequest;
import Response.LoginRespond;

import java.util.UUID;

public class LoginServices {
    public LoginRespond loginUser(LoginRequest loginRequest) throws DataAccessException {
        UserDAO userDAO = new SQLUserDAO();
        AuthDAO authDAO =  new SQLAuthDao();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        UserData userData = new UserData(username,password,null);
        if (username == null || password == null || !userDAO.verifyUser(userData)){
            throw new DataAccessException("Error: unauthorized");
        } else if (userDAO.verifyUser(userData)) {
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData(username, authToken);
            authDAO.createAuthToken(authData);
            return new LoginRespond(username,authToken);
        }
        throw new DataAccessException("Error: description");
    }
}