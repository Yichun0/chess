package server.Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import server.RequestResponses.ErrorResponse;
import server.RequestResponses.JoinGameRequest;
import Service.JoinGameServices;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();
        String authToken = request.headers("authorization");
        JoinGameServices joinGame = new JoinGameServices();
        JoinGameRequest joinGameRequest = gson.fromJson(request.body(), JoinGameRequest.class);
        try {
            joinGame.JoinGame(joinGameRequest,authToken);
            response.status(200);
            return "{}";
        } catch (DataAccessException e) {
            if (e.getMessage().equals("Error: unauthorized")) {
                response.status(401);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            } else if (e.getMessage().equals("Error: bad request")) {
                response.status(400);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            }
            response.status(403);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }
}
