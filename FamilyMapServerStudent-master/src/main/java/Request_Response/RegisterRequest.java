package Request_Response;

/**
 * Creates a new user account (user row in the database)
 * Generates 4 generations of ancestor data for the new user
 * (just like the /fill endpoint if called with a generations value of 4
 * and this new user’s username as parameters)
 * Logs the user in
 * Returns an authtoken
 */
public class RegisterRequest extends AbstractRequest{
    /**
     * The users username
     */
    private String username;
    /**
     * the users password
     */
    private String password;
    /**
     *Users email
     */
    private String email;
    /**
     * Users first name
     */
    private String firstName;
    /**
     * users last name
     */
    private String lastName;
    /**
     * Users gender either F or M
     */
    private String gender;

    /**
     * Intializes the data for the register request ex, the username,password,email etc
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public RegisterRequest() {}

    public void stringify()
    {
        super.toString(this.username,this.password,this.email,this.firstName,this.lastName,this.gender);
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
