package Service;

import Request_Response.RegisterRequest;
import Request_Response.RegisterResponse;

/**
 * THis will carry out the /user/register webAPI
 */
public class RegisterService {

    /**
     * Executes the web api called by the user in this case to register a new user
     * @param regReq this is the RegisterRequest object that will have the details we need to register the new user
     * @return This returns the response body for the HTTP response
     */
    RegisterResponse register(RegisterRequest regReq){return null;}

}
