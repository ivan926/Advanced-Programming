package Request_Response;

/**
 * Logs the user in
 * Returns an authtoken.
 */
public class LoginRequest extends AbstractRequest {
    /**
     * The username of the user
     */
    private String username;
    /**
     * The last secret password of the user
     */
    private String password;

    /**
     * Initalizes the value instead of using setters
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {

    }


    public void stringify() {
        super.toString(this.username, this.password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
