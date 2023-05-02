package Request_Response;

import model.authtoken;

/**
 * without any additional parameters, it returns ALL family members of the current user.
 * The user is determined from the provided auth token
 *
 * If parameters are included (specified eventID) Returns the single vent object with the specified ID
 * (if the event is associated with the current user) The current user is determined from the provided auth
 * token
 */
public class EventRequest {
    private String eventID = null;
    private Boolean optionalParam = null;
    private authtoken AuthorizationToken;

    public EventRequest(String personID, Boolean optionalParam,authtoken authtoken)
    {
        this.eventID = personID;
        this.optionalParam = optionalParam;
        this.AuthorizationToken = authtoken;
    }

    public EventRequest(Boolean optionalParam,authtoken authtoken)
    {
        this.optionalParam = optionalParam;
        this.AuthorizationToken = authtoken;
    }

    public EventRequest(authtoken authtoken)
    {
        this.AuthorizationToken = authtoken;
    }

    public EventRequest() {

    }

    public void setEventID(String eventID)
    {
        this.eventID = eventID;
    }

    public void setAuthorizationToken(authtoken authorizationToken) {
        AuthorizationToken = authorizationToken;
    }

    public void setOptionalParam(Boolean optionalParam) {
        this.optionalParam = optionalParam;
    }

    public Boolean getOptionalParam() {
        return optionalParam;
    }

    public String getEventID() {
        return eventID;
    }

    public authtoken getAuthorizationToken() {
        return AuthorizationToken;
    }

}
