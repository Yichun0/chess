package server.Handlers;

import com.google.gson.Gson;
import exception.DataAccessException;
import Response.ErrorResponse;
import Requests.RegisterRequest;
import Response.RegisterResult;
import Service.RegisterServices;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        try{
            Gson gson = new Gson();
            RegisterRequest registerRequest = gson.fromJson(request.body(),RegisterRequest.class);
            RegisterServices regServ = new RegisterServices();
            response.status(200);
            RegisterResult successRep = regServ.registerUser(registerRequest);
            return gson.toJson(successRep);
        }
        catch (DataAccessException e){
            Gson gson = new Gson();
            if (e.getMessage().equals("Error: bad request")){
                response.status(400);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            }
            else if(e.getMessage().equals("Error: already taken")){
                response.status(403);
                return gson.toJson(new ErrorResponse(e.getMessage()));
            }
            response.status(500);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }
}
