package server.Handlers;

import dataAccess.DataAccessException;
import Service.ClearServices;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    public ClearServices clearServices;
    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        ClearServices clearServices = new ClearServices();
        clearServices.clearData();
        response.status(200);
        response.body();
        return "{}";
    }
}
