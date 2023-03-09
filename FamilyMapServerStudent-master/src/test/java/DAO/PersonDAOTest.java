package DAO;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {

    private Database db;
    private Person bestPerson;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessError {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = Database.getInstance();
        // and a new event with random data
        bestPerson = new Person("23ssd", "navis462", "John",
                "Smith", "M", "23sd", "dfg45",
                "98fg");

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        pDao = new PersonDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        pDao.clear();
    }



    @Test
    public void insertPass() throws DataAccessError {
        // Start by inserting an event into the database.
        pDao.insertPerson(bestPerson);
        // Let's use a find method to get the event that we just put in back out.
        Person compareTest = pDao.getPersonObject(bestPerson.getPersonID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessError {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        pDao.insertPerson(bestPerson);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessError.class, () -> pDao.insertPerson(bestPerson));
    }

    @Test
    public void retrievePass() throws DataAccessError {

       Person secondPerson = new Person("5j4","nunki","harold","stephenson","F","sdfv","23jsdnx","lajd4d");
        pDao.insertPerson(bestPerson);

        pDao.insertPerson(secondPerson);

        Person retrievedObject = pDao.getPersonObject("5j4");


        assertNotEquals(pDao.getPersonObject(bestPerson.getPersonID()),retrievedObject);

        assertEquals(secondPerson,retrievedObject);

    }

    @Test
    public void retrieveFail() throws DataAccessError {

        Person secondPerson = new Person("5j4","nunki","harold","stephenson","F","sdfv","23jsdnx","lajd4d");
        pDao.insertPerson(bestPerson);

        pDao.insertPerson(secondPerson);

        Person retrievedObject = pDao.getPersonObject("6778");

        //the personID should be null as it does not exist
        assertEquals(null,retrievedObject.getPersonID());


    }


    @Test
    public void clearSuccess() throws DataAccessError {
        //person is inserted
        pDao.insertPerson(bestPerson);
        pDao.insertPerson(new Person("5j4","nunki","harold","stephenson","F","sdfv","23jsdnx","lajd4d"));

        //Person person = pDao.getPersonObject(bestPerson.getPersonID());
        //data base is cleared from any person

        int count =  pDao.clear();

        //two objects should be deleted
        assertEquals(2,count);

        //try clearing again and the count of delete should be zero there are none.

        count =  pDao.clear();

        assertEquals(0,count);



    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }


}
