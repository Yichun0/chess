package server.Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import Model.AuthData;
import Requests.CreateGameRequest;
import Response.CreateGameRespond;
import Response.ErrorResponse;
import Service.CreateGameServices;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();
        CreateGameRequest createGameRequest = gson.fromJson(request.body(),CreateGameRequest.class);
        CreateGameServices createGameServices = new CreateGameServices();
        AuthData authToken = new AuthData(null,request.headers("authorization"));
        try {
            CreateGameRespond successRep = createGameServices.createGame(createGameRequest,authToken);
            response.status(200);
            response.body(gson.toJson(successRep));
            return gson.toJson(successRep);
        } catch (DataAccessException e) {
            if (e.getMessage().equals("Error: bad request")){
                response.status(400);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            }
            else if (e.getMessage().equals("Error: unauthorized")) {
                response.status(401);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            }
            response.status(500);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }
}
