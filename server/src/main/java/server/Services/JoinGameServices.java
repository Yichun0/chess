package server.Services;

import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.GameDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DAO.MemoryGameDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import dataAccess.Model.GameData;
import server.RequestResponses.JoinGameRequest;

import java.util.Objects;

import static java.util.Objects.isNull;

public class JoinGameServices {
    public void JoinGame(JoinGameRequest joinRequest, String authToken) throws DataAccessException {
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO =  new MemoryAuthDAO();
        String playerColor = joinRequest.getPlayerColor();
        int gameID = joinRequest.getGameID();
        try {
            GameData game = gameDAO.getGame(gameID);
            String username = authDAO.getUsername(new AuthData(null,authToken));
            if (playerColor == null){
                joinAsWatcher();
                return;
            }
            if (!playerColor.equalsIgnoreCase("white") && !playerColor.equalsIgnoreCase("black")) {
                throw new DataAccessException("error: bad request");
            }
            else if (playerColor.equalsIgnoreCase("white")) {
                if (!isNull(game.getWhiteUsername())) {
                    throw new DataAccessException("Error: already taken");
                } else {
                    game.setWhiteUsername(username);
                    gameDAO.createGame(game);
                }
            } else if(playerColor.equalsIgnoreCase("black")){
                if (!isNull(game.getBlackUsername())) {
                    throw new DataAccessException("Error: already taken");
                } else {
                    game.setBlackUsername(username);
                    gameDAO.createGame(game);
                }
            }
        } catch (DataAccessException e){
            throw e;
        }
    }
    public void joinAsWatcher(){
    }
}
