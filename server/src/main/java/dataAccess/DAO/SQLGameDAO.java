package dataAccess.DAO;

import Model.GameData;
import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import exception.DataAccessException;
import dataAccess.DatabaseManager;
import Requests.JoinGameRequest;

import java.sql.*;
import java.util.*;

public class SQLGameDAO implements GameDAO {
    public int newGameID;
    public static Map<Integer, GameData> gameDatas = new HashMap<>();

    public void clearGameDAO() throws DataAccessException {
        String statement = "TRUNCATE gameTable";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public int createGame(GameData gameData) throws DataAccessException {
        Gson gson = new Gson();
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        ChessGame game = new ChessGame();
        game.setBoard(board);
        String gameString = gson.toJson(game);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO gameTable (blackUsername, WhiteUsername, gameName,chessGame) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, gameData.getBlackUsername());
            preparedStatement.setString(2, gameData.getWhiteUsername());
            preparedStatement.setString(3,gameData.getGameName());
            preparedStatement.setString(4,gameString);
            preparedStatement.executeUpdate();
            var result =  preparedStatement.getGeneratedKeys();
            if (result.next()){
                return result.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    public int getGameID(String gameName) throws DataAccessException, SQLException {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT gameID FROM gameTable WHERE gameName = ?")) {
            preparedStatement.setString(1, gameName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    newGameID = rs.getInt("game_id");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        newGameID++;
        return newGameID;
    }
    public boolean playerTaken(int gameID, String playerColor, String username){
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT whiteUsername, blackUsername FROM gameTable WHERE gameID = ?")) {
            preparedStatement.setInt(1, gameID);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    if (playerColor.equalsIgnoreCase("white")) {
                        // whiteUser
                        String whiteUser = result.getString("whiteUsername");
                        if (!Objects.equals(whiteUser, username)) {
                            return true;
                        }
                    } else {
                        String blackUser = result.getString("blackUsername");
                        if (!Objects.equals(blackUser, username)) {
                            return true;
                        }
                    }
                }
            }
        }
        catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public String getGame(int gameID) throws DataAccessException {
        String chessGame = null;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT chessGame FROM gameTable WHERE gameID = ?")) {
            preparedStatement.setInt(1, gameID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    chessGame = rs.getString("chessGame");

                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return chessGame;
    }

        public String getGameName (int gameID) throws DataAccessException {
        String gameName = null;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT gameName FROM gameTable WHERE gameID = ?")) {
            preparedStatement.setInt(1, gameID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    gameName = rs.getString("gameName");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return gameName;
    }
    public boolean findGame(int gameID) throws DataAccessException {
        // check whether the game exit in the database or not
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM gameTable WHERE gameID = ?")) {
            preparedStatement.setInt(1, gameID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            } catch (SQLException se) {
                return false;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

    public void joinGame(String username, JoinGameRequest request ) throws DataAccessException{
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT whiteUsername, blackUsername FROM gameTable WHERE gameID = ?")) {
            preparedStatement.setInt(1, request.getGameID());
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    if (request.getPlayerColor() == null) {
                        return; //join as watcher
                    } else if (request.getPlayerColor().equalsIgnoreCase("white")) {
                        // whiteUser
                        String whiteUser = result.getString("whiteUsername");
                        if (whiteUser != null) {
                            throw new DataAccessException("Error: already taken");
                        } else {
                            PreparedStatement stm = connection.prepareStatement("UPDATE gameTable SET whiteUsername =? Where gameID = ? ");
                            stm.setString(1, username);
                            stm.setInt(2, request.getGameID());
                            stm.executeUpdate();
                        }
                    } else {
                        //black user
                        String blackUser = result.getString("blackUsername");
                        if (blackUser != null) {
                            throw new DataAccessException("Error: already taken");
                        } else {
                            PreparedStatement stm = connection.prepareStatement("UPDATE gameTable SET blackUsername =? Where gameID = ? ");
                            stm.setString(1, username);
                            stm.setInt(2, request.getGameID());
                            stm.executeUpdate();
                        }
                    }
                } else {
                    throw new DataAccessException("Error: bad request");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public Collection<GameData> listGame() throws DataAccessException {
        List<GameData> games = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM gameTable");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var gameID = resultSet.getInt("gameID");
                var blackUsername = resultSet.getString("blackUsername");
                var whiteUsername = resultSet.getString("whiteUsername");
                var gameName = resultSet.getString("gameName");
                var game = resultSet.getString("chessGame");
                // turning game back to chess game object using gson
                Gson gson  = new Gson();
                ChessGame chessGame = gson.fromJson(game, ChessGame.class);
                games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }
}
