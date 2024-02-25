package server.Handlers;

import com.google.gson.Gson;
import dataAccess.DAO.AuthDAO;
import dataAccess.DAO.MemoryAuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Model.AuthData;
import server.RequestResponses.ErrorResponse;
import server.RequestResponses.LoginRequest;
import server.RequestResponses.LogoutRequest;
import server.Services.ClearServices;
import server.Services.LoginServices;
import server.Services.LogoutServices;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        AuthData authToken = new AuthData(null,request.headers("authorization"));
        LogoutServices logoutServices = new LogoutServices();
        try {
            logoutServices.logoutServices(authToken);
            response.status(200);
            return "{}";
        }
        catch (DataAccessException e){
            Gson gson = new Gson();
            response.status(401);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }
}
