package dataAccess.DAO;

import dataAccess.DataAccessException;
import Model.UserData;

public interface UserDAO {
    void createUser(UserData userData) throws DataAccessException;
    boolean findUser(UserData userData) throws DataAccessException;
    void clearUserDAO() throws DataAccessException;
    void createPassword(UserData userData) throws DataAccessException;
    boolean findPassword(UserData userData) throws DataAccessException;
}
