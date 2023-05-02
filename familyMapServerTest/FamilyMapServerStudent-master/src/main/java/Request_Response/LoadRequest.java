package Request_Response;

/**
 * Clears all data from the database (just like the /clear API)
 * Loads the user, person, and event data from the request body into the database.
 */
public class LoadRequest {
    /**
     * Array of users past by the load web API
     */
    String[] user;
    /**
     * Array of persons past by the load web API
     */
    String[] person;
    /**
     * Array of event past by the load web API
     */
    String[] event;

    public LoadRequest(String[] user, String[] person, String[] event) {
        this.user = user;
        this.person = person;
        this.event = event;
    }

    public String[] getUser() {
        return user;
    }

    public String[] getPerson() {
        return person;
    }

    public String[] getEvent() {
        return event;
    }
}
