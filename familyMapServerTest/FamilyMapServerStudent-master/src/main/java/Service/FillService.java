package Service;

import Request_Response.FillRequest;
import Request_Response.FillResponse;

/**
 * This will execute the /fill/ webAPI for the specified user
 */
public class FillService {
    /**
     * This will fill in a certain amount of generations of ancestors, either specifically from the user
     * or 4 by default, as long as the username is registered. If data exists, it is deleted
     * @param fillReq this is our request Body with properties needed to manipulate our database
     * @return returns our response body for our http response
     */
    FillResponse fill(FillRequest fillReq){return null;}
}
