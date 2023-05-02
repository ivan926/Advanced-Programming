package Request_Response;

import model.authtoken;

/**
 * Returns ALL family members of the current user. The current user is determined by the provided authtoken.
 *WITHOUT PARAMETERS
 *
 *WITH PARAMETERS
 * Returns the single Person object with the specified ID (if the person is
 * associated with the current user). The current user is determined by the provided authtoken.
 *
 */



public class PersonRequest {


    private String personID = null;
    private Boolean optionalParam = null;
    private authtoken AuthorizationToken;

    public PersonRequest(String personID, Boolean optionalParam,authtoken authtoken)
    {
        this.personID = personID;
        this.optionalParam = optionalParam;
        this.AuthorizationToken = authtoken;
    }

    public PersonRequest(Boolean optionalParam,authtoken authtoken)
    {
        this.optionalParam = optionalParam;
        this.AuthorizationToken = authtoken;
    }

    public PersonRequest(authtoken authtoken)
    {
        this.AuthorizationToken = authtoken;
    }

    public PersonRequest() {

    }

    public void setOptionalParam(Boolean optionalParam) {
        this.optionalParam = optionalParam;
    }

    public void setAuthorizationToken(authtoken authorizationToken) {
        AuthorizationToken = authorizationToken;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Boolean getOptionalParam() {
        return optionalParam;
    }

    public String getPersonID() {
        return personID;
    }

    public authtoken getAuthorizationToken() {
        return AuthorizationToken;
    }
}
