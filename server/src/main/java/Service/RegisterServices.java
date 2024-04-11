package Service;

import dataAccess.DAO.*;
import exception.DataAccessException;
import Model.AuthData;
import Model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import Requests.RegisterRequest;
import Response.RegisterResult;

import java.util.UUID;

public class RegisterServices {
    public RegisterResult registerUser(RegisterRequest registerRequest) throws DataAccessException {

        UserDAO userDao = new SQLUserDAO();
        AuthDAO authDAO = new SQLAuthDao();
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();
        if (username == null || password == null || email == null){
            throw new DataAccessException("Error: bad request");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        UserData userData = new UserData(username, hashedPassword, email);
        try {
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData(username, authToken);
            userDao.createUser(userData);
            authDAO.createAuthToken(authData);
            return new RegisterResult(username, authToken);
        }
        catch (DataAccessException exp){
            throw exp;
        }
    }
}
