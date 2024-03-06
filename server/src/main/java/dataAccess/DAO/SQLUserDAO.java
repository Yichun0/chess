package dataAccess.DAO;

import Model.UserData;
import dataAccess.DataAccessException;

import java.util.HashMap;
import java.util.Map;

public class SQLUserDAO implements UserDAO{
    public static Map<String, UserData> userDAO = new HashMap<>();

    public void clearUserDAO(){
        userDAO.clear();
    }

    public void createUser(UserData userData){
        userDAO.put(userData.getUsername(),userData);
    }

    public boolean findUser(UserData userData) {
        if (userDAO.containsKey(userData.getUsername())){
            return true;
        }
        return false;
    }
    public void createPassword(UserData userData){
        userDAO.put(userData.getPassword(),userData);
    }
    public boolean findPassword(UserData userData) {
        if (userDAO.containsKey(userData.getPassword())){
            return true;
        }
        return false;
    }
}
