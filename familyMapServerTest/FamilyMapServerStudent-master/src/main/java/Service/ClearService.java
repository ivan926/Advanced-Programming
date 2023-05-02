package Service;

import Request_Response.ClearRequest;
import Request_Response.ClearResponse;

/**
 * This clears the data from all the databases using the /clear webAPI
 */
public class ClearService {
    /**
     * this request will delete all data in the data base
     * @param clearReq contains information to know clear request desires to be executed
     * @return returns our success or response body for the clear function
     */
    ClearResponse clear(ClearRequest clearReq){return null;}
}
