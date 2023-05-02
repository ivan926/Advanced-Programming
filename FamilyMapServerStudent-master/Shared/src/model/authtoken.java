package model;

import java.util.Objects;

/**
*Auth token object has the users random generated token with the username property to pair with the token
*/

public class authtoken {
/**
 * Authorization random token generated for the user
 */
private String authToken = null;
/**
 * The users created username
 */
private String username = null;

/**
 * Takes in the users username and Authorization token to initialize instance variables
 *
 * @param  authToken the authorization token
 * @param username the username
 */
public authtoken(String authToken, String username) {
    this.authToken = authToken;
    this.username = username;
}

public String getAuthToken() {
    return authToken;
}

public void setAuthToken(String authToken) {
    this.authToken = authToken;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

    /**
     * this is an equals object to check to see if another object has the same username and auth token
     * @param o a generic object at first which will be cast to appropriate class
     * @return will return either true or false depending one whether objects match false if they do not match
     */

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    authtoken authtoken = (model.authtoken) o;
    return authToken.equals(authtoken.authToken) && username.equals(authtoken.username);
}

    /**
     *
     * @return returns potential hash if item is wished to be stored in a collection, could be used to verify if user has already registered
     */
@Override
public int hashCode() {
    return Objects.hash(authToken, username);
}
}
