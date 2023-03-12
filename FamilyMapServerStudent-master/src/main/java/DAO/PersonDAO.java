package DAO;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PersonDAO is the abstract interface that interacts explicitly with the person table
 */
public class PersonDAO {


    private final Connection conn;

    public PersonDAO(Connection conn)
    {
        this.conn = conn;
    }



    /**
     * We are deleting(clearing) all rows
     * @throws DataAccessError exception
     */
    public int clear()throws DataAccessError{
        System.out.println("Attempting to clear person table");
        String sql = "delete from Person";
        int count = 0;

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {

            count = stmt.executeUpdate();



            System.out.printf("Deleted %d person\n", count);
        } catch (SQLException throwables) {
            throw new DataAccessError("Could not delete from table");
        }

        return count;
    }

    /**
     * We are removing specified person and associated properties
     * @param personID The row that the user wishes to remove using some sort of identifier
     * @throws DataAccessError exception
     */
    public void removePerson (String personID)throws DataAccessError{}

    /**
     * We will update a table, by editing values within the rows maybe UID, firstname,lastname,gender etc
     * @param person we will pass through the person object and use it to retrieve the personID
     *  so we know the current actual data in memory
     *  @throws DataAccessError exception
     */
    public void update(Person person)throws DataAccessError{}

    /**
     * THis will populate the server database with generated data for the specified user object with
     * properties that will help us create an appropriate person object
     * By default 4 generations will be created
     * This must be user already registered. If there is any data already associated with the user we delete it
     * @param person object with updated properties
     * @throws DataAccessError exception
     */
    public void fill(Person person)throws DataAccessError{

    }


    /**
     *This will be a an optional parameter similar to the fill with a user object which will have
     * properties such as first name last name, and gender. This will be used to create a model object
     * or update it
     * It lets the user decide the number of generations, it must be a non negative number (The default is 4)
     * which resulsts in 31 new persons with each associated events
     * @param person model object that will have important information needed
     * @param generations
     * @throws DataAccessError exception
     * not within range error/not an int error
     */
    public void fill(Person person, int generations)throws DataAccessError{}

    /**
     * Gets a peron object using an ID
     * @param personID
     //* @param authtoken auth token needed to retrieve an event
     * @return a person object
     * @throws DataAccessError exception
     */
    public Person getPersonObject(String personID)throws DataAccessError{

       // List<Book> books = new ArrayList<>();

        String sql = "SELECT personID, AssociatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID FROM Person " +
                "WHERE personID = ?";
        //String sql = "SELECT * FROM table_name WHERE id = ?";
        Person person;
        try(PreparedStatement stmt = conn.prepareStatement(sql))
        {

                stmt.setString(1, personID);

                 ResultSet rs = stmt.executeQuery();

                String perID = rs.getString("personID");
                String associatedUsername = rs.getString("AssociatedUsername");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String gender = rs.getString("gender");
                String fatherID = rs.getString("fatherID");
                String motherID = rs.getString("MotherID");
                String spouseID = rs.getString("spouseID");



                  person = new Person(perID,associatedUsername,firstName,lastName,gender,fatherID,motherID,spouseID);

        } catch (SQLException throwables) {
            throw new DataAccessError("Person could not be retrieved");


        }





        return person;


    }

    /**
     * Gets all family members of the individual using their username to find associated family members
     * @param username
    // * @param authtoken auth token needed to retrieve an event
     * @return a list of family members are returned
     * @throws DataAccessError exception
     */
    public Person[] getFamilyMembers(String username)throws DataAccessError { return null; }

    /**
     * This will receive a list of people, this is used in conjunction with the load function
     * @param listOfPeople array of person objects
     * @throws DataAccessError
     */
    public void insertPeople(Person[] listOfPeople)throws DataAccessError{}

    public void insertPerson(Person person)throws DataAccessError{

        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Person (personID, AssociatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());


            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessError("Error encountered while inserting an event into the database");
        }


    }




}
