package DAO;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class LoginDAOTest {

    private Database db;
    private User bestUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessError {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = Database.getInstance();
        // and a new event with random data
        bestUser = new User("xavi777","disnatas","Lin@y.com","Linus","pickmen","F","3456");

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        uDao = new UserDAO(conn);


    }

    @Test
    void Logintest() throws DataAccessError {

        uDao.getUserById("navina");

    }
}
