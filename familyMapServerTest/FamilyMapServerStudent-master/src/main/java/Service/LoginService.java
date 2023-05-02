package Service;

import Request_Response.LoginRequest;
import Request_Response.LoginResponse;

/**
 * This will carry out the /user/login webAPI
 */
public class LoginService {
    /**
     * This will log the user in and return an auth token
     * @param logReq this contains our request body with the properties needed to log the user in
     * @return returns the response body for our http response
     */
    public LoginResponse login(LoginRequest logReq){return null;}
}
