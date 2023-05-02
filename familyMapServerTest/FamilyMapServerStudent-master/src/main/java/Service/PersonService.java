package Service;

import Request_Response.PersonRequest;
import Request_Response.PersonResponse;

/**
 * This will carre out the webAPI of /person/ and its optional ID parameter
 */
public class PersonService {
    /**
     * This will either return a single person object or an array of person's object
     * @param personReq
     * @return This will retunr our response body for our http
     */
    PersonResponse person(PersonRequest personReq){return null;}
}
