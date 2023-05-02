package Request_Response;

/**
 * This will contain a response success or error
 */
public class LoginResponse {
    public LoginResponse() {

    }

    /**
     * Prints out the response body
     */
    public void printRequest(){}

    /**
     * This is the authorization token
     */
    private String authtoken = null;
    /**
     * The users username
     */
    private String username = null;
    /**
     * The person ID
     */
    private String personID = null;
    /**
     * Boolean value that will let you know if the response was a success or not
     */
    private Boolean success = null;
    /**
     * Message explaining error
     */
    private String message = null;

    /**
     * Constructor used if the response is a success
     * @param authtoken
     * @param username
     * @param personID
     */
    public LoginResponse(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = true;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    /**
     * constructor used if response is an error
     * @param message The error message
     */
    public LoginResponse(String message) {

        this.message = message;
        this.success = false;
    }
}
