package server.Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import server.RequestResponses.ErrorResponse;
import server.RequestResponses.ListGamesRespond;
import Service.ListGamesServices;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();
        String authData = (request.headers("authorization"));
        try {
            ListGamesServices listGame = new ListGamesServices();
            ListGamesRespond successRep = listGame.listGames(authData);
            response.status(200);
            response.body(gson.toJson(successRep));
            return gson.toJson(successRep);
        } catch (DataAccessException e) {
            if (e.getMessage().equals("Error: unauthorized")) {
                response.status(401);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            }
            response.status(500);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }
}
