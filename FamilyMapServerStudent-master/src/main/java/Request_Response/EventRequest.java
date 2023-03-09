package Request_Response;

/**
 * without any additional parameters, it returns ALL family members of the current user.
 * The user is determined from the provided auth token
 *
 * If parameters are included (specified eventID) Returns the single vent object with the specified ID
 * (if the event is associated with the current user) The current user is determined from the provided auth
 * token
 */
public class EventRequest {

    /**
     * no request body
     */
}
