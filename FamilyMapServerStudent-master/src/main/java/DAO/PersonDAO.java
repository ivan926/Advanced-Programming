package DAO;

import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * PersonDAO is the abstract interface that interacts explicitly with the person table
 */
public class PersonDAO extends DAO{


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
     * This method will help in clearing the person table for a user who has information
     * people related to them, we will use associated username to find them
     * @param associatedUsername
     * @throws DataAccessError
     */
    public int removePeople(String associatedUsername)throws DataAccessError{
        String sql = "DELETE FROM Person WHERE associatedUsername = ? ";

        int numberOfPeopleRemoved = 0;
        try (PreparedStatement stmnt = conn.prepareStatement(sql)){

            stmnt.setString(1,associatedUsername);

            numberOfPeopleRemoved = stmnt.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
       // System.out.println(numberOfPeopleRemoved);
        return numberOfPeopleRemoved;
    }




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
    public Person[] getFamilyMembers(String username)throws DataAccessError {
        String sql = "SELECT * FROM Person WHERE associatedUsername = ? ";

        Person[] personArray = null;
        try(PreparedStatement stmnt = conn.prepareStatement(sql)){
            stmnt.setString(1,username);

            ResultSet rs =  stmnt.executeQuery();


            int arrayIndex = 0;
            int totalPeople = 0;
            //get total rows
            while(rs.next())
            {
                totalPeople++;
            }

            personArray = new Person[totalPeople];

            //reset pointer to top of the data table row
            rs =  stmnt.executeQuery();

            while(rs.next())
            {
                personArray[arrayIndex] = (     new Person(rs.getString(1),rs.getString(2),rs.getString(3),
                      rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),
                      rs.getString(8)));



                arrayIndex++;
            }


        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        //System.out.println(personArray[0].getPersonID());
        return personArray;

    }

    /**
     * This will receive a list of people, this is used in conjunction with the load function
     * @param listOfPeople array of person objects
     * @throws DataAccessError
     */
    public void insertPeople(Person[] listOfPeople)throws DataAccessError{
        int sizeofList = listOfPeople.length;
        int arrayIndex = 0;
        Person person;
        System.out.println(sizeofList);
        while(arrayIndex < sizeofList)
        {

          //  System.out.println(arrayIndex);
            String PERSONID = null;
            String ASSOCIATED_USERNAME = null;
            String FIRST_NAME = null;
            String LAST_NAME = null;
            String GENDER = null;
            String FATHER_ID = null;
            String MOTHER_ID = null;
            String SPOUSE_ID = null;

            PERSONID = listOfPeople[arrayIndex].getPersonID();
            ASSOCIATED_USERNAME = listOfPeople[arrayIndex].getAssociatedUsername();
            FIRST_NAME = listOfPeople[arrayIndex].getFirstName();
            LAST_NAME = listOfPeople[arrayIndex].getLastName();
            GENDER = listOfPeople[arrayIndex].getGender();
            if(listOfPeople[arrayIndex].getGender() != null)
            {
                GENDER = GENDER.toLowerCase();
            }
            FATHER_ID = listOfPeople[arrayIndex].getFatherID();
            MOTHER_ID = listOfPeople[arrayIndex].getMotherID();
            SPOUSE_ID = listOfPeople[arrayIndex].getSpouseID();

            person = new Person(PERSONID,ASSOCIATED_USERNAME,FIRST_NAME,LAST_NAME,GENDER,FATHER_ID,MOTHER_ID,SPOUSE_ID);

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
                System.out.println(e.getMessage());
                throw new DataAccessError(e.getErrorCode(),e.getMessage());
            }

            arrayIndex++;
        }




    }

    public int insertPerson(Person person)throws DataAccessError{

        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Person (personID, AssociatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        int peopleInserted = 0;
        String gender = person.getGender();
        if(person.getGender() != null)
        {
            gender = gender.toLowerCase();
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, gender);
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());


            peopleInserted = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessError("Error encountered while inserting an event into the database");
        }

        return peopleInserted;
    }




}
