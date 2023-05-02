package Request_Response;

/**
 * Returns the success response body or error response body
 */
public class LoadResponse {

    /**
     * Prints out the response body
     */
    public void printRequest(){}

    /**
     * Message of either an error or success
     */
    private String message;
    /**
     * a true or false variable depending on whether a response was successful or not
     */
    private Boolean success;

    /**
     * Initialize response values
     *
     * @param message
     * @param success
     */
    public LoadResponse(String message, Boolean success) {
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
