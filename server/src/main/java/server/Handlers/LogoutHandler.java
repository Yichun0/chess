package server.Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import server.RequestResponses.ErrorResponse;
import server.RequestResponses.LoginRequest;
import server.RequestResponses.LogoutRequest;
import server.Services.ClearServices;
import server.Services.LoginServices;
import server.Services.LogoutServices;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        try {
            Gson gson = new Gson();
            LogoutServices logoutServices = new LogoutServices();
            logoutServices.logoutServices();
            response.status(200);
            response.body();
            return "{}";
        }
        catch (DataAccessException e){
            Gson gson = new Gson();
            if (e.getMessage().equals("Error: unauthorized")){
                response.status(401);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            }
            response.status(500);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }
}
