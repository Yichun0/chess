package server.Handlers;

import com.google.gson.Gson;
import exception.DataAccessException;
import Model.AuthData;
import Response.ErrorResponse;
import Service.LogoutServices;
import spark.Request;
import spark.Response;
import spark.Route;

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
            ErrorResponse errorResponse = new  ErrorResponse(e.getMessage());
            response.body(gson.toJson(errorResponse));
            return gson.toJson(errorResponse);
        }
    }
}
