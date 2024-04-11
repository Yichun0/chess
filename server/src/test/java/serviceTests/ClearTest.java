package serviceTests;

import Service.ClearServices;
import dataAccess.DAO.*;
import Model.AuthData;
import org.junit.jupiter.api.Test;
import server.Handlers.ClearHandler;


import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {
    @Test
    public void positiveTest() throws Exception {
        AuthDAO authDAO = new SQLAuthDao();
        AuthData authData = new AuthData("username", "authToken");
        authDAO.createAuthToken(authData);
        ClearServices services = new ClearServices();
        services.clearData();
        assertEquals(false, authDAO.findAuthToken(authData.getAuthToken()));
    }
}
