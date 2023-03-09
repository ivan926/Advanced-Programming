package Service;
import DAO.DataAccessError;
import Request_Response.RegisterResponse;
import Service.RegisterService;
import Request_Response.RegisterRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RegisterServiceTest {
    RegisterService regService;
    RegisterRequest regRequest;
    RegisterResponse regResponse;

    Gson gson;
    @BeforeEach
    void preperationVariables()
    {

        regService = new RegisterService();
        regRequest = new RegisterRequest();
        regResponse = new RegisterResponse();

        gson = new Gson();

    }

    @AfterEach
    void deconstruction()
    {



    }


    @Test
    void UserNameAlreadyBeingUsedError()
    {
        String reqData = "{ \"username\": \"existent\", \"password\": \"bananas\", \"email\":\"navis463@pmail.com\"," +
                "\"firstName\": \"Defenia\", \"lastName\":\"Baraniston\",\"gender\":\"m\" }";

        regRequest = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);

        regResponse = regService.register(regRequest);

        assertEquals("Error: Username already taken",regResponse.getMessage());

        //assertThrows(DataAccessError.class, ()-> regService.register(regRequest));



    }

    @Test
    void missingPropertyInRequestBodyError()
    {
        String reqData = "{ \"password\": 45, \"email\":\"navis463@pmail.com\"," +
                "\"firstName\": \"Defenia\", \"lastName\":\"Baraniston\",\"gender\":\"F\" }";

        regRequest = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);

        regResponse = regService.register(regRequest);

        assertEquals(false,regResponse.isSuccess());
        assertEquals("Error: Request Property missing",regResponse.getMessage());

    }

    @Test
    void incorrectPropertyValueForGender()
    {
        String reqData = "{ \"username\": \"existent\", \"password\": \"bananas\", \"email\":\"navis463@pmail.com\"," +
                "\"firstName\": \"Defenia\", \"lastName\":\"Baraniston\",\"gender\":\"67\" }";

        regRequest = (RegisterRequest) gson.fromJson(reqData, RegisterRequest.class);

        regResponse = regService.register(regRequest);

        assertEquals(false,regResponse.isSuccess());
        assertEquals("Error: Invalid value",regResponse.getMessage());

    }




}
