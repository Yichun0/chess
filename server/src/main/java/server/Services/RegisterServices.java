package server.Services;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryUserDAO;
import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import dataAccess.Model.UserData;
import server.RequestResponses.RegisterRequest;
import server.RequestResponses.RegisterResult;

import java.util.UUID;

public class RegisterServices {
    public RegisterResult registerUser(RegisterRequest registerRequest) throws DataAccessException {
        UserDAO userDao = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
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
            userDao.createPassword(userData);
            authDAO.createAuthToken(authData);
            return new RegisterResult(username, authToken);
        }
        throw new DataAccessException("Error: description");
    }
}
