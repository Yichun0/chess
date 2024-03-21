package clientTests;

import exception.ResponseException;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade = new ServerFacade("http://localhost:8080");
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerPos() throws ResponseException {
        String username = "user";
        String password = "password";
        String email = "email";
        assertDoesNotThrow(() -> serverFacade.register(username, password, email));
    }

    @Test
    public void registerNeg() throws ResponseException{
        String username = "user";
        String password = "password";
        String email = "email";
        assertThrows(ResponseException.class, ()-> serverFacade.register(username,password,email));
    }

    @Test
    public void loginPos() throws ResponseException{
        String username = "user";
        String password = "password";
        assertDoesNotThrow(() -> serverFacade.login(username,password));
    }

    @Test
    public void loginNeg() throws ResponseException{
        String username = "user";
        String password = "Wrongpassword";
        assertThrows(ResponseException.class, () -> serverFacade.login(username,password));
    }

    @Test
    public void logoutPos() throws ResponseException{
        assertDoesNotThrow(() -> serverFacade.logout());
    }

    @Test
    public void logoutNeg() throws ResponseException{
        ServerFacade emptyUrl = new ServerFacade(null);
        assertThrows(ResponseException.class, emptyUrl::logout);
    }

    @Test
    public void createGame() throws ResponseException{
        String gameName = "NewGame";
        assertDoesNotThrow(()-> serverFacade.createGame(gameName));
    }
}
