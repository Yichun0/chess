package Service;

import chess.ChessGame;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.GameData;
import server.RequestResponses.CreateGameRequest;
import server.RequestResponses.CreateGameRespond;

public class CreateGameServices {
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
            int gameID = gameDAO.listGame().size() + 1;
            gameDAO.createGame(new GameData(gameID, null, null, gameName, new ChessGame()));
            return new CreateGameRespond(gameID);
        }
    }
}
