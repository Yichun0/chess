package server;

import spark.*;

public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    public int run() {
        Spark.port(8080);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
