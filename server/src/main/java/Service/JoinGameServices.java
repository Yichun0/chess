package Service;

import dataAccess.DAO.*;
import dataAccess.DataAccessException;
import Model.AuthData;
import Model.GameData;
import server.Requests.JoinGameRequest;

import static java.util.Objects.isNull;

public class JoinGameServices {
    public void joinGame(JoinGameRequest joinRequest, String authToken) throws DataAccessException {
        GameDAO gameDAO = new SQLGameDAO();
        AuthDAO authDAO =  new SQLAuthDao();
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