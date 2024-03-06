package server.Handlers;

import dataAccess.DataAccessException;
import Service.ClearServices;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;

public class ClearHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws DataAccessException, SQLException {
        ClearServices clearServices = new ClearServices();
        clearServices.clearData();
        response.status(200);
        response.body();
        return "{}";
    }
}