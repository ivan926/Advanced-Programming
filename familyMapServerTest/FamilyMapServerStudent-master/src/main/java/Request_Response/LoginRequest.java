package Request_Response;

/**
 * Logs the user in
 * Returns an authtoken.
 */
public class LoginRequest {
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
