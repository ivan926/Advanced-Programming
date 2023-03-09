package Service;

import Request_Response.LoadRequest;
import Request_Response.LoadResponse;

/**
 * This will execute the /load webAPI
 */
public class LoadService {
    /**
     * This will clear all data from the database and then populate the database with appropriate user,
     * person, and event information.
     * @param loadReq Will contain the necessary req data with possible array of user, person, and event data
     * @return will return the response body which will contain a message and the success boolean value
     */
    LoadResponse load(LoadRequest loadReq){return null;}
}
