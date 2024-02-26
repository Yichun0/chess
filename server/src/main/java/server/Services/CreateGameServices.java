package server.Services;

import chess.ChessGame;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import dataAccess.Model.GameData;
import server.RequestResponses.CreateGameRequest;
import server.RequestResponses.CreateGameRespond;

import java.util.UUID;

public class CreateGameServices {
    int gameID = 1;
    public CreateGameRespond createGame(CreateGameRequest gameRequest, AuthData authToken) throws DataAccessException {
        String gameName = gameRequest.getGameName();
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        if (gameName == null){
            throw new DataAccessException("Error: bad request");
        } else if (!authDAO.findAuthToken(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
        else {
            gameDAO.createGame(new GameData(gameID, null, null, gameName, new ChessGame()));
            gameID += 1;
            return new CreateGameRespond(gameID-1);
        }
    }
}
