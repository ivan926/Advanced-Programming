package DAO;

import model.User;
import model.authtoken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the abstraction for the Authtoken table, only component that interfaces with
 * the authToken table
 */
public class AuthtokenDAO extends DAO {

    Connection conn = null;

    public AuthtokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * This will create a new token using the username
     *
     * @param authToken a string that will help us create an auth token
     * @return this will bring back an authtoken which is a feature found when a user registers or logs in
     * @throws DataAccessError exception
     */
    public String createToken(authtoken authToken) throws DataAccessError {
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
     * We will retrieve an auth token using an authtoken
     *
     * @param authtoken will be used to find the generated token for the user in question
     * @return Authtoken object with current authorization token.
     * @throws DataAccessError exception
     */
    public authtoken getAuthToken(String authtoken) throws DataAccessError {

        String sql = "SELECt * FROM \"Authorization Token\" WHERE authtoken = ?";
        String SQLauthtoken = null;
        String username = null;
        authtoken authtokenOBJ = null;

        try (PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setString(1, authtoken);

            ResultSet rs = stmnt.executeQuery();


            SQLauthtoken = rs.getString("authtoken");
            username = rs.getString("username");
            authtokenOBJ = new authtoken(SQLauthtoken, username);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return authtokenOBJ;
    }

    /**
     * We are deleting(clearing) all rows
     *
     * @throws DataAccessError exception
     */
    public int clear() throws DataAccessError {

        int count = 0;
        System.out.println("Inside the clear for auth token");

        String sql = "delete from \"Authorization Token\"";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            count = stmt.executeUpdate();



            System.out.printf("Deleted %d Auth Tokens\n", count);
        } catch (SQLException throwables) {
            throw new DataAccessError("Could not delete from Auth table");
        }

        return count;

    }


}
