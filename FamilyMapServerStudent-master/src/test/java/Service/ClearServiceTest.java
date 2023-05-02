package Service;

import Request_Response.ClearRequest;
import Request_Response.ClearResponse;
import Request_Response.LoginRequest;
import Request_Response.LoginResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClearServiceTest {

    ClearService clearService;
    ClearRequest clearRequest;
    ClearResponse clearResponse;

    Gson gson;
    @BeforeEach
    void preperationVariables()
    {
    clearService = new ClearService();
    clearRequest = new ClearRequest();
    clearResponse = new ClearResponse();

    gson = new Gson();

}

    @AfterEach
    void deconstruction()
    {



    }

    @Test
    void CLearSuccess()
    {
        String reqData = "{\"username\":\"existent\",\"password\":\"bananas\"}";

        String reqData2 = "{\"username\":\"different\",\"password\":\"lexicon\"}";

      String clearStringResponse =  "{\"message\":\"Clear succeeded.\",\"success\":true}";
        //clearRequest = gson.fromJson(clearStringResponse,ClearRequest.class);
       clearResponse = clearService.clear(clearRequest);
       String gsonString = gson.toJson(clearResponse);

       assertEquals(clearStringResponse,gsonString);


    }



}
