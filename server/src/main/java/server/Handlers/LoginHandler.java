package server.Handlers;

import Requests.LoginRequest;
import com.google.gson.Gson;
import exception.DataAccessException;

import Service.LoginServices;
import Response.ErrorResponse;
import Response.LoginRespond;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        try{
            Gson gson = new Gson();
            LoginRequest loginRequest = gson.fromJson(request.body(),LoginRequest.class);
            LoginServices loginServ = new LoginServices();
            response.status(200);
            LoginRespond successRep = loginServ.loginUser(loginRequest);
            return gson.toJson(successRep);
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