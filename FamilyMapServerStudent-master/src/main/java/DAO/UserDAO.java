package DAO;

import model.User;
import org.sqlite.SQLiteErrorCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User Data access object interfaces only with the user table, it contains function that manipulate
 * the user table for in our database.
 */
public class UserDAO extends DAO{
    /**
     * this is the connection to the database it will remain static
     */
    private static Connection conn;
    public UserDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * We will create a user when a user registers for the first time
     * @param user This will be our model object that will contain the new user information
     */
    public void createUser(User user)throws DataAccessError{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO User (username, password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";

        String gender = user.getGender();
        if(user.getGender() != null)
        {
           gender = gender.toLowerCase();
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, gender);
            stmt.setString(7, user.getPersonID());



            stmt.executeUpdate();
            System.out.println("\nExecuting SQL statements");
        } catch (SQLException e ) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new DataAccessError(e.getErrorCode(),e.getMessage());
           // throw new DataAccessError( String.valueOf(e.getErrorCode()));

        }

        System.out.println("User has been created");
    }

    public Boolean isDuplicate(String username)
    {
        Boolean userNameExist = false;

        System.out.println("Finding out if duplicate username exist");
        System.out.println("Current username is " + username);
        String sql = "SELECT username FROM User WHERE username = ?";
        String SQLUsername = null;
        System.out.println("Query has been set");
        try(PreparedStatement stmnt = conn.prepareStatement(sql))
        {
            System.out.println("In try catch setting place holder to value");
            stmnt.setString(1,username);
            System.out.println("Executing query");
           ResultSet rs = stmnt.executeQuery();
            System.out.println("Iterating result set");
            while(rs.next())
            {
                SQLUsername = rs.getString("username");


            }
            System.out.println("Value of SQLUSERNAME = "+SQLUsername );

            if(username.equals(SQLUsername))
            {
                System.out.println("Username already exists");
                userNameExist = true;
            }

            System.out.println("Exiting successfully");

        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
            System.out.println("Something has gone wrong when verifying duplicate");

        }



        return userNameExist;
    }

    /**
     * This will validate the user when they are trying to log in,
     * this function is used to verify if the username and password combination does exist
     * @param UserName The username
     * @param Password The password of the user
     * @return true or false, false if the username and password combination is not found in the database
     */
    public boolean validate(String UserName, String Password) throws DataAccessError {
        String sql = "SELECT username, password, email FROM User" + " WHERE username = ? AND password = ?";
        String usernameSQL = null;
        String passwordSQL = null;

        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,UserName);
            stmt.setString(2,Password);

            ResultSet rs = stmt.executeQuery();


            while(rs.next()) {

                usernameSQL = rs.getString("username");

                passwordSQL = rs.getString("password");
            }
            System.out.println(usernameSQL + " " + passwordSQL);
            if(usernameSQL == null && passwordSQL == null)
            {
                return false;
            }


        } catch (SQLException throwables) {
            throwables.getLocalizedMessage();
            throwables.printStackTrace();
            throw new DataAccessError("User could not be verified");


        }

        return true;}

    /**
     * This will return a user object found in the data table using the unique identifier of the user.
     * @param userID The users UID
     * @return Will return the users object along with its properties
     */
    public User getUserById(String userID)throws DataAccessError{

        String sql = "select * FROM User WHERE username = ?";
        User user;

        try(PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, userID);
                ResultSet rs = stmt.executeQuery();



            String username = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String gender = rs.getString("Gender");
            String personID = rs.getString("personID");


            user = new User(username,password,email,firstName,lastName,gender,personID);

        } catch (SQLException throwables) {
            throw new DataAccessError("User could not be retrieved");


        }





        return user;



    }

    /**
     * This will return a user object found in the data table using the username.
     * @param username The users unique username
     * @return Will return the users object along with its properties
     */
    public User getUserByUsername(String username) throws DataAccessError {
        String sql = "select username, password, email, firstName, lastName, gender, personID FROM User " +
                "where username = ?";
        User user;
        try(PreparedStatement stmt = conn.prepareStatement(sql))
             {
                 stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();


                 String usersName = rs.getString("username");
                 String password = rs.getString("password");
                 String email = rs.getString("email");
                 String firstName = rs.getString("firstName");
                 String lastName = rs.getString("lastName");
                 String gender = rs.getString("Gender");
                 String personID = rs.getString("personID");



                 user = new User(usersName,password,email,firstName,lastName,gender,personID);

        } catch (SQLException throwables) {
            throw new DataAccessError("User could not be retrieved");


        }





        return user;
    }


    /**
     * We are deleting(clearing) all rows
     * @throws DataAccessError exception
     * @return
     */
    public int clear()throws DataAccessError{

        int count = 0;

        String sql = "delete from User";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {

             count = stmt.executeUpdate();


            System.out.printf("Deleted %d users\n", count);
        } catch (SQLException throwables) {
            throw new DataAccessError("Could not delete from User table");
        }

        return count;
    }




}
