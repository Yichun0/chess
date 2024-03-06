package Service;

import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.UserData;
import server.Requests.RegisterRequest;
import server.Response.RegisterResult;

import java.util.UUID;

public class RegisterServices {
    public RegisterResult registerUser(RegisterRequest registerRequest) throws DataAccessException {
        UserDAO userDao = new SQLUserDAO();
        AuthDAO authDAO = new SQLAuthDao();
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();
        UserData userData = new UserData(username, password, email);
        if (username == null || password == null || email == null){
            throw new DataAccessException("Error: bad request");
        }
        else if(userDao.findUser(userData)){
            throw new DataAccessException("Error: already taken");
        }
        else if(!userDao.findUser(userData)){
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData(username, authToken);
            userDao.createUser(userData);
            authDAO.createAuthToken(authData);
            return new RegisterResult(username, authToken);
        }
        throw new DataAccessException("Error: description");
    }
}
