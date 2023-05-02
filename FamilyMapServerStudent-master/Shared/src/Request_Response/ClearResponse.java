package Request_Response;

/**
 * This class will contain our message and our boolean value indicating whether request was successful
 */
public class ClearResponse {
    public ClearResponse() {

    }

    /**
     * print message and boolean value
     */
    public void printString(){

    }

    /**
     * The error or success message
     */
    private String message;
    /**
     * The boolean value which is true or false depending on whether is was a success or not.
     */
    private Boolean success;

    /**
     * Constructor which will compelte data for success or error response
     * @param message error or success message
     * @param success true or false
     */
    public ClearResponse(String message, Boolean success) {
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
