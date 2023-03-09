package Request_Response;

/**
 * Will have success or error message
 */
public class FillResponse {
    /**
     * Prints out the response body
     */
    public void printRequest(){}


    /**
     * the message which will either be negative or positive
     */
    private String message;
    /**
     * This will either be a success response or error response
     */
    private Boolean success;

    /**
     * Constructor that will get a failed response or successful response
     * @param message
     * @param success
     */
    public FillResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}
