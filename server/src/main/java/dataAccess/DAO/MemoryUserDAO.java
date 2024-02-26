package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO{
    public static Map<String, UserData> userDAO = new HashMap<>();

    public void clearUserDAO() throws DataAccessException {
        userDAO.clear();
    }

    public void createUser(UserData userData) throws DataAccessException{
        userDAO.put(userData.getUsername(),userData);
    }

    public boolean findUser(UserData userData) throws DataAccessException{
        if (userDAO.containsKey(userData.getUsername())){
            return true;
        }
        return false;
    }
    public void createPassword(UserData userData) throws DataAccessException{
        userDAO.put(userData.getPassword(),userData);
    }
    public boolean findPassword(UserData userData) throws DataAccessException{
        if (userDAO.containsKey(userData.getPassword())){
            return true;
        }
        return false;
    }
}


