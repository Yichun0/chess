package server.Handlers;

import com.google.gson.Gson;
import server.RequestResponses.ClearResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    public ClearHandler ClearServiceHandler;

    public  ClearHandler(ClearHandler ClearServiceHandler){
        this.ClearServiceHandler = ClearServiceHandler;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson respond = new Gson();
        ClearResponse result = ClearService
    }
}
