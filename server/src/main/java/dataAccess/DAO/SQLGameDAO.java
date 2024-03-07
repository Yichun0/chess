package dataAccess.DAO;

import Model.GameData;
import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import server.Response.CreateGameRespond;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLGameDAO implements GameDAO {
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
    public CreateGameRespond createGame(String gameName) throws DataAccessException {
        Gson gson = new Gson();
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        ChessGame game = new ChessGame();
        game.setBoard(board);
        String gameString = gson.toJson(game);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO gameTable (gameName,chessGame) VALUES (?,?,?,?)")) {
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(5, gameString);
            preparedStatement.executeUpdate();
            var result =  preparedStatement.getGeneratedKeys();
            var ID = 0;
            if (result.next()){
                ID = result.getInt(1);
            }
            return new CreateGameRespond(ID);
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public boolean findGame(String gameName, int gameID) throws DataAccessException {
        GameData game = new GameData(gameID, null, null, gameName, null);
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM gameTable WHERE gameID = ?")) {
            preparedStatement.setInt(1, game.getGameID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            } catch (SQLException se) {
                return false;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

    public GameData getGame(int gameID) throws DataAccessException {
        GameData game = gameDatas.get(gameID);
        if (game == null) {
            throw new DataAccessException("Error: bad request");
        }
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT gameName FROM gametable WHERE gameID = ?")) {
            preparedStatement.setInt(1, gameID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String gameName = resultSet.getString("gameName");
                    return new GameData(gameID, null, null, gameName, new ChessGame());
                }
            }
            return null;
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
                var game = resultSet.getString("game");
                // turning game back to chess game object using gson
                Gson gson  = new Gson();
                ChessGame chessGame = gson.fromJson(game, ChessGame.class);
                games.add(new GameData(gameID, blackUsername, whiteUsername, gameName, chessGame));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }
}
