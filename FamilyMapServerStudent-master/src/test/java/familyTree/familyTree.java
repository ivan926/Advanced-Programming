package familyTree;

import DAO.*;
import Service.FamilyTree;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class familyTree {

    User dummyUser;
    FamilyTree tree;
    Database database;
    Connection conn;
    PersonDAO personDAO;
    UserDAO userDAO;
    EventDAO eventDAO;


    @BeforeEach
    void setUp() throws DataAccessError {
        dummyUser = new User("example123","Aegon","vale@ymail.com","Vary",
                "Stormwind","m","12345");

        database = Database.getInstance();

        conn = database.getConnection();



    }

    @AfterEach
    void destructor() throws DataAccessError {
     // personDAO = new PersonDAO(conn);

    }

    @Test
    void treeGenerationTest()
    {
        tree = new FamilyTree(dummyUser,conn);

        tree.fillTree();



    }

    @Test
    void treeGenerationTestWithParameters()
    {
        tree = new FamilyTree(dummyUser,conn);

        tree.fillTree(0);



    }

    @Test
    void clearUserTable() throws DataAccessError {

        userDAO = new UserDAO(conn);
        userDAO.clear();

        database.closeConnection(true);


    }

    @Test
    void clearPersonTable() throws DataAccessError {

        personDAO = new PersonDAO(conn);
        personDAO.clear();

        database.closeConnection(true);


    }

    @Test
    void clearEventTable() throws DataAccessError {

        eventDAO = new EventDAO(conn);
        eventDAO.clear();

        database.closeConnection(true);


    }

}
