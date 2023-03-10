package DAO;

import model.User;
import model.authtoken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This is the abstraction for the Authtoken table, only component that interfaces with
 * the authToken table
 */
public class AuthtokenDAO {

    Connection conn = null;

    public AuthtokenDAO(Connection conn)
    {
        this.conn = conn;
    }
    /**
     * This will create a new token using the username
     * @param authToken a string that will help us create an auth token
     * @throws DataAccessError exception
     * @return this will bring back an authtoken which is a feature found when a user registers or logs in
     */
    public String createToken(authtoken authToken)throws DataAccessError{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String stringAuthToken;
        String sql = "INSERT INTO \"Authorization Token\" (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());




            stmt.executeUpdate();
            System.out.println("Executing SQL statements");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessError("Error encountered while inserting authtoken and username into database");
        }
        System.out.println("\nSending authtoken as a string");
        return authToken.getAuthToken();
    }

    /**
     * We will retrieve an auth token using the username of the user
     * @param username will be used to find the generated token for the user in question
     * @return Authtoken object with current authorization token.
     * @throws DataAccessError exception
     */
    public authtoken getTokenByUsername(String username)throws DataAccessError{return null;}

    /**
     * We will retrieve an auth token using an authtoken
     * @param authtoken will be used to find the generated token for the user in question
     * @return Authtoken object with current authorization token.
     * @throws DataAccessError exception
     */
    public authtoken getAuthToken(String authtoken)throws DataAccessError{return null;}

    /**
     * We are deleting(clearing) all rows
     * @throws DataAccessError exception
     */
    public void clear()throws DataAccessError{}

    /**
     * We are removing the token using their username
     * @param username will be used as key to find the row that contains the token we want to remove
     * @throws DataAccessError exception
     */
    public void removeAuthToken(String username)throws DataAccessError{}

    /**
     * We will update the username if the user decided to change it
     * @param authtoken we will pass through an object that is a model of the database and update the results
     *  so we know the current data in memory
     *  @throws DataAccessError exception
     */
    public void updateUsername(authtoken authtoken)throws DataAccessError{}

    /**
     * If the auth token has expired or needs to be regenerated, this function will do just that
     * @param authtoken the auth token object with the current token generated.
     * @throws DataAccessError exception
     * @return the authorization token needed for certain requests
     */
    public String regenerateAuthToken(authtoken authtoken){return null;}
}
