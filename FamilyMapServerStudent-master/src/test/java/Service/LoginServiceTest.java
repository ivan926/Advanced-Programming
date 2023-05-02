package Service;

import Request_Response.LoginRequest;
import Request_Response.LoginResponse;
import Request_Response.RegisterRequest;
import Request_Response.RegisterResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginServiceTest {

    LoginService loginService;
    LoginRequest loginRequest;
    LoginResponse logResponse;

    Gson gson;
    @BeforeEach
    void preperationVariables()
    {

        loginService = new LoginService();
        loginRequest = new LoginRequest();
        logResponse = new LoginResponse();

        gson = new Gson();

    }

    @AfterEach
    void deconstruction()
    {



    }

    @Test
    void loginSuccess()
    {
        String reqData = "{\"username\":\"existent\",\"password\":\"bananas\"}";

        String reqData2 = "{\"username\":\"different\",\"password\":\"lexicon\"}";
        loginRequest = (LoginRequest) gson.fromJson(reqData, LoginRequest.class);

        String json1 = gson.toJson(loginRequest);

        assertEquals(reqData, json1);
        logResponse = loginService.login(loginRequest);

        loginRequest = (LoginRequest) gson.fromJson(reqData2, LoginRequest.class);

        String json2 = gson.toJson(loginRequest);
        logResponse = loginService.login(loginRequest);

        assertEquals(json2,reqData2);

    }

    @Test
    void loginFail()
    {
        String reqData = "{\"username\":\"existent\"}";


        String reqData2 = "{\"username\":\"different\",\"password\":\"lexicon\"}";
        loginRequest = (LoginRequest) gson.fromJson(reqData, LoginRequest.class);

        String json1 = gson.toJson(loginRequest);


        logResponse = loginService.login(loginRequest);
       // System.out.println(gson.toJson(logResponse));
        assertFalse(logResponse.getSuccess());

    }

//    @Test
//    void UserNameAlreadyBeingUsedError()
//    {
//        String reqData = "{ \"username\": \"existent\", \"password\": \"bananas\", \"email\":\"navis463@pmail.com\"," +
//                "\"firstName\": \"Defenia\", \"lastName\":\"Baraniston\",\"gender\":\"m\" }";
//
//
//        regRequest = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);
//
//        logResponse = loginService.register(regRequest);
//
//        assertEquals("Error: Username already taken", logResponse.getMessage());
//
//        //assertThrows(DataAccessError.class, ()-> regService.register(regRequest));
//
//
//
//    }
//
//    @Test
//    void missingPropertyInRequestBodyError()
//    {
//        String reqData = "{ \"password\": 45, \"email\":\"navis463@pmail.com\"," +
//                "\"firstName\": \"Defenia\", \"lastName\":\"Baraniston\",\"gender\":\"F\" }";
//
//        regRequest = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);
//
//        logResponse = loginService.register(regRequest);
//
//        assertEquals(false, logResponse.isSuccess());
//        assertEquals("Error: Request Property missing", logResponse.getMessage());
//
//    }
//
//    @Test
//    void incorrectPropertyValueForGender()
//    {
//        String reqData = "{ \"username\": \"existent\", \"password\": \"bananas\", \"email\":\"navis463@pmail.com\"," +
//                "\"firstName\": \"Defenia\", \"lastName\":\"Baraniston\",\"gender\":\"67\" }";
//
//        regRequest = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);
//
//        logResponse = loginService.register(regRequest);
//
//        assertEquals(false, logResponse.isSuccess());
//        assertEquals("Error: Invalid value", logResponse.getMessage());
//
//    }
}
