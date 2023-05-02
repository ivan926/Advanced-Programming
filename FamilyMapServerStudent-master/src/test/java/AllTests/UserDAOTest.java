package AllTests;

import DAO.DataAccessError;
import DAO.Database;
import DAO.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private Database db;
    private User bestUser;
    private User bestUser2;
    private UserDAO uDao;
    private Connection conn;

    @BeforeEach
    public void setUp() throws DataAccessError {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = Database.getInstance();
        // and a new event with random data
        bestUser = new User("xavi777","disnatas","Lin@y.com","Linus","pickmen","f","3456");
        bestUser2 = new User("ponyita","disnatas","Lin@y.com","Linus","pickmen","f","3456");
        // Here, we'll open the connection in preparation for the test case to use it
        conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        uDao = new UserDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        /// TODO: 3/8/23 Need to use clear

    }

    @Test
    public void validateUserSuccess() throws DataAccessError {
      //  bestUser = new User("pernango","sixtam","Latlabartixy.com","pesxxcv","pickmen","F","3456");
        uDao.createUser(bestUser);
        String username = bestUser.getUsername();
        String password = bestUser.getPassword();
        assertEquals(true, uDao.validate(username, password));

        uDao.clear();

    }

    @Test
    public void validateUserFalse() throws DataAccessError {

        uDao.createUser(bestUser);

        assertEquals(false, uDao.validate("xavi777", "disnata"));


    }

    @Test
    public void createUserSuccess() throws DataAccessError {
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
    public void createUserFail() throws DataAccessError {

        //username already is being used
        uDao.createUser(bestUser);

        assertThrows(DataAccessError.class, () -> uDao.createUser(bestUser));
    }

    @Test
    public void getUserByIDSuccess() throws DataAccessError {

      //  User secondPerson = new User("8734","notimportant","niki","white","larken","F","8792");
        uDao.createUser(bestUser);

        uDao.createUser(bestUser2);
        String username = bestUser.getUsername();
        User retrievedObject = uDao.getUserByUsername(username);


        assertEquals(bestUser,retrievedObject);

    }

    @Test
    public void getUserByIDFail() throws NullPointerException, DataAccessError {
        //no username exists
       // User newUser = new User("8734","notimportant","niki","white","larken","F","8792");
        uDao.createUser(bestUser);

       // uDao.createUser(secondPerson);

        User retrievedObject = uDao.getUserByUsername("FAKE");

        assertNull(retrievedObject.getUsername());


    }


    @Test
    public void clearSuccess() throws DataAccessError {
        //person is inserted
        uDao.clear();
        User newPerson = new User("8734","notimportant","niki","white","larken","F","8792");
        uDao.createUser(bestUser);
        uDao.createUser(newPerson);
        //Person person = pDao.getPersonObject(bestPerson.getPersonID());
        //data base is cleared from any person

        int count =  uDao.clear();

        //two objects should be deleted
        assertEquals(2,count);

        //try clearing again and the count of delete should be zero there are none.

        count =  uDao.clear();

        assertEquals(0,count);



    }

    @Test
    public void isDuplicateSuccess() throws DataAccessError {
        uDao.createUser(bestUser2);
        String username = bestUser2.getUsername();
        assertTrue(uDao.isDuplicate(username));


    }
    @Test
    public void isDuplicateFailure()
    {
        assertFalse(uDao.isDuplicate("NAVI465"));


    }

    @Test
    void getUserByUsernameFail() throws DataAccessError {
        User newPerson = new User();
        newPerson = uDao.getUserByUsername("fakeUsername");
        assertNull(newPerson.getUsername());
    }

    @Test
    void getUserByUsernameSuccess() throws DataAccessError {
        User copy = new User();
        uDao.createUser(bestUser);
        String username = bestUser.getUsername();
        copy = uDao.getUserByUsername(username);
        assertEquals(bestUser,copy);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        conn.rollback();

    }






}
