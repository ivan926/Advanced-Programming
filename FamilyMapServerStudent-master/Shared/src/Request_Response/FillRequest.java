package Request_Response;

/**
 * Populates the server's database with generated data for the specified username. The required "username"
 * parameter must be a user already registered with the server.
 * If there is any data in the database already associated with the given username, it is deleted.
 * The optional "generations" parameter lets the caller specify the number of generations of ancestors
 * to be generated,
 * and must be a non-negative integer (the default is 4, which results in 31 new persons each with
 * associated events).
 * More details can be found in the earlier section titled “Family History Information Generation”
 */
public class FillRequest {


    private int generations = 0;

    private String username = null;
    /**
     * Default constructor that will generate default value of 4 generations
     * @param username this will
     */
    public FillRequest(String username)
    {
        this.username = username;
        this.generations = 4;
    }

    public FillRequest(){}

    public FillRequest(String username, int generations)
    {
        this.username = username;
        this.generations = generations;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void stringify() {
        System.out.printf("Username = %s generation %s",username,generations);
    }

    /**This constructor takes in the optional generation parameter
     *
     * @param username the username
     * @param generations the number of generations to create
     */



    /**
     * There will be no request body
     */
}
