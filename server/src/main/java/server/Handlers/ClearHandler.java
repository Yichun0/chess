package server.Handlers;

import com.google.gson.Gson;
import server.RequestResponses.ClearResponse;
import server.Services.ClearServices;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {

    public  ClearHandler(){
    }


    public Object handle(Request request, Response response) throws Exception {
//        String req = request.body(); // Json
//        Gson reqObj = new Gson();
//        reqObj =reqObj.fromJson(req, )
        ClearResponse clearResult = ClearServices();
        return "";
    }
}
