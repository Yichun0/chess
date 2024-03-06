package server;

import server.Handlers.*;
import spark.*;

public class Server {
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int port) {
        Spark.port(port);

        Spark.staticFiles.location("web");
        ClearHandler clearHandler = new ClearHandler();
        Spark.delete("/db", clearHandler);

        RegisterHandler registerHandler = new RegisterHandler();
        Spark.post("/user", registerHandler);

        LoginHandler loginHandler = new LoginHandler();
        Spark.post("/session", loginHandler);

        LogoutHandler logoutHandler = new LogoutHandler();
        Spark.delete("/session", logoutHandler);

        ListGameHandler listGameHandler = new ListGameHandler();
        Spark.get("/game", listGameHandler);

        CreateGameHandler createGameHandler = new CreateGameHandler();
        Spark.post("/game",createGameHandler);

        JoinGameHandler joinGameHandler = new JoinGameHandler();
        Spark.put("/game", joinGameHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}