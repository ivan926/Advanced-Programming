package FileHanderTests;

import FileHandler.LoginHandler;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginHandlerTest {
    LoginHandler loginHand;
    HttpExchange exchange;
    String serverHost;
    String serverPort;

    @BeforeAll
    void setupValues()
    {
        loginHand = new LoginHandler();
        serverHost = "localhost";
        serverPort = "8080";
    }

    @Test
    void loginTest()
    {



    }

}
