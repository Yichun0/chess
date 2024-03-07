package dataAccess.DAO;

import Model.UserData;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLUserDAO implements UserDAO {
    public static Map<String, UserData> userDAO = new HashMap<>();

    public void clearUserDAO() throws DataAccessException {
        String statement = "TRUNCATE userTable";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void createUser(UserData userData) throws DataAccessException {
        var statement = "INSERT INTO userTable (username, password, email) VALUES (?,?,?) ";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1, userData.getUsername());
            preparedStatement.setString(2, userData.getPassword());
            preparedStatement.setString(3, userData.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error: already taken");
        }
    }
//    public boolean findUser(UserData userData) throws DataAccessException{
//        try (Connection connection = DatabaseManager.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM userTable WHERE username = ?")) {
//            preparedStatement.setString(1, userData.getUsername());
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
////                    String password = resultSet.getString("password");
////                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
////                    return encoder.matches(userData.getPassword(), password);
//                }
//            }
//        } catch (SQLException e) {
//            return false;
//        }
//        return false;
//    }

    public boolean verifyUser(UserData userData) throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM userTable WHERE username = ?")) {
            preparedStatement.setString(1, userData.getUsername());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString("password");
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    return encoder.matches(userData.getPassword(), password);
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}