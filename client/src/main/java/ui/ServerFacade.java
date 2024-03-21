package ui;

import Model.GameData;
import Requests.CreateGameRequest;
import Requests.JoinGameRequest;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Response.*;
import com.google.gson.Gson;
import exception.ResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collection;

public class ServerFacade {
    private final String serverUrl;
    public String authToken;
    public ServerFacade(String url){
        serverUrl = url;
    }

    public void register(String username, String password, String email) throws ResponseException {
        String path = "/user";
        RegisterRequest requestBody =  new RegisterRequest(username, password, email);
        this.makeRequest("POST", path, requestBody, RegisterResult.class);
    }

    public void logout() throws ResponseException {
        String path = "/session";
        this.makeRequest("DELETE", path, null, null);
    }


    public LoginRespond login(String username, String password) throws ResponseException {
        var path = "/session";
        LoginRequest requestBody = new LoginRequest(username, password);
        return this.makeRequest("POST", path, requestBody, LoginRespond.class);
    }

    public Collection<GameData> listGames() throws ResponseException {
        String path = "/game";
        var response =  this.makeRequest("GET", path, null, ListGamesRespond.class);
        return response.getGames();
    }

    public CreateGameRespond createGame(String gameName) throws ResponseException {
        String path = "/game";
        CreateGameRequest request = new CreateGameRequest(gameName);
        CreateGameRespond respond = this.makeRequest("POST",path,request, CreateGameRespond.class);
        return respond;
    }

    public void joinGame(int gameID, String color) throws ResponseException {
        String path = "/game";
        JoinGameRequest request = new JoinGameRequest(color, gameID);
        this.makeRequest("PUT", path, request, null);
    }


    public void quit() throws ResponseException {
        String path = "/db";
        this.makeRequest("DELETE", path, null, ClearResponse.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (authToken != null) {
                http.addRequestProperty("authorization", authToken);
            }
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}

