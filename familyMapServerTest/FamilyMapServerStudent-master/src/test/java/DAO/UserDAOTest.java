package DAO;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private Database db;
    private User bestUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessError {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestUser = new User("Navis462","secret","Lin@y.com","Linus","pickmen","F","3456");

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        uDao = new UserDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        uDao.clear();
    }


    @Test
    public void insertPass() throws DataAccessError {
        // Start by inserting an event into the database.
        uDao.createUser(bestUser);
        // Let's use a find method to get the event that we just put in back out.
        User compareTest = uDao.getUserByUsername(bestUser.getUsername());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessError {

        //username already is being used
        uDao.createUser(bestUser);

        assertThrows(DataAccessError.class, () -> uDao.createUser(bestUser));
    }

    @Test
    public void retrievePass() throws DataAccessError {

        User secondPerson = new User("8734","notimportant","niki","white","larken","F","8792");
        uDao.createUser(bestUser);

        uDao.createUser(secondPerson);

        User retrievedObject = uDao.getUserByUsername("8734");


        assertNotEquals(uDao.getUserByUsername(bestUser.getPersonID()),retrievedObject);

        assertEquals(secondPerson,retrievedObject);

    }

    @Test
    public void retrieveFail() throws NullPointerException, DataAccessError {
        //no username exists
        User secondPerson = new User("8734","notimportant","niki","white","larken","F","8792");
        uDao.createUser(bestUser);

        uDao.createUser(secondPerson);

        User retrievedObject = uDao.getUserByUsername("notExisti");


        assertEquals(null,retrievedObject.getPersonID());


    }


    @Test
    public void clearSuccess() throws DataAccessError {
        //person is inserted
        User secondPerson = new User("8734","notimportant","niki","white","larken","F","8792");
        uDao.createUser(bestUser);
        uDao.createUser(secondPerson);
        //Person person = pDao.getPersonObject(bestPerson.getPersonID());
        //data base is cleared from any person

        int count =  uDao.clear();

        //two objects should be deleted
        assertEquals(2,count);

        //try clearing again and the count of delete should be zero there are none.

        count =  uDao.clear();

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
