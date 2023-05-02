package Request_Response;

/**
 * Will have success response or error
 */
public class RegisterResponse {

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
    private boolean success;
    /**
     * Message explaining error
     */
    private String message = null;

    /**
     * This is the success response constructor
     * @param authtoken
     * @param username
     * @param personID
     */
    public RegisterResponse(String authtoken, String username, String personID) {
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

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    /**
     * this is the error response constructor
     * @param message error message which will vary
     */
    public RegisterResponse(String message) {
        this.message = message;
        this.success = false;
    }
}
