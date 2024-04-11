package dataAccess.DAO;

import Model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO{
    public static Map<String, UserData> userDAO = new HashMap<>();

    public void clearUserDAO(){
        userDAO.clear();
    }

    public void createUser(UserData userData){
        userDAO.put(userData.getUsername(),userData);
    }

    public boolean verifyUser(UserData userData) {
        if (userDAO.containsKey(userData.getUsername())){
            return true;
        }
        return false;
    }
}


