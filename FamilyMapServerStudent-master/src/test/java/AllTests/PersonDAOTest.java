package AllTests;

import DAO.DataAccessError;
import DAO.Database;
import DAO.EventDAO;
import DAO.PersonDAO;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersonDAOTest {

    private Database db;
    private Person bestPersonMaster;
    private Person bestPersonChild1;
    private Person bestPersonChild2;
    private Person bestPersonChild3;
    private Person person2;
    private Person[] persons;

    private PersonDAO pDao;
    private EventDAO eventDAO;
    private Connection conn;

    @BeforeEach
    public void setUp() throws DataAccessError {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = Database.getInstance();

        // and a new event with random data
        bestPersonMaster = new Person("23ssd", "navis462", "Kenneth",
                "White", "m", "23sd", "dfg45",
                "98fg");

        bestPersonChild1 = new Person("Nonka", "navis462", "Rock",
                "Wood", "m", "23sd", "dfg45",
                "98fg");

        bestPersonChild2 = new Person("monar", "navis462", "lila",
                "Smith", "m", "23sd", "dfg45",
                "98fg");

        bestPersonChild3 = new Person("kimlart", "navis462", "Pedro",
                "Knocker", "m", "23sd", "dfg45",
                "98fg");

        person2 = new Person("heller", "coconama", "John",
                "Smith", "m", "23sd", "dfg45",
                "98fg");

        persons = new Person[]{bestPersonMaster,bestPersonChild2,bestPersonChild3,bestPersonChild1};

        // Here, we'll open the connection in preparation for the test case to use it
        conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        pDao = new PersonDAO(conn);

        eventDAO = new EventDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
       pDao.clear();
    }



    @Test
    public void insertPersonPassSuccess() throws DataAccessError {
        int peopleInserted = 0;
        // Start by inserting an event into the database.
        peopleInserted = pDao.insertPerson(bestPersonMaster);
        // Let's use a find method to get the event that we just put in back out.

        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertEquals(1,peopleInserted);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
    }

    @Test
    public void insertPersonFail() throws DataAccessError {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        pDao.insertPerson(bestPersonMaster);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessError.class, () -> pDao.insertPerson(bestPersonMaster));
    }

    @Test
    public void getPersonObjectSuccess() throws DataAccessError {

       Person secondPerson = new Person("5j4","nunki","harold","stephenson","F","sdfv","23jsdnx","lajd4d");
        pDao.insertPerson(bestPersonMaster);
        String personID = bestPersonMaster.getPersonID();

        pDao.insertPerson(secondPerson);


        Person retrievedObject = pDao.getPersonObject(personID);


      // assertNotEquals(pDao.getPersonObject(bestPerson.getPersonID()),retrievedObject);
        System.out.println(bestPersonMaster.equals(retrievedObject));
        assertEquals(bestPersonMaster,retrievedObject);

    }

    @Test
    public void getPersonObjectFail() throws DataAccessError {

        Person secondPerson = new Person("5j4","nunki","harold","stephenson","F","sdfv","23jsdnx","lajd4d");
        pDao.insertPerson(bestPersonMaster);

        pDao.insertPerson(secondPerson);

        Person retrievedObject = pDao.getPersonObject("6778");

        //the personID should be null as it does not exist
        assertEquals(null,retrievedObject.getPersonID());


    }


    @Test
    public void clearSuccess() throws DataAccessError {
        //person is inserted
        pDao.insertPerson(bestPersonMaster);
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

    @Test
    void getFamilyMembersSuccess() throws DataAccessError {
        Person[] personArray = null;
        pDao.insertPeople(persons);
        String username = bestPersonMaster.getAssociatedUsername();
        personArray = pDao.getFamilyMembers(username);
        System.out.println(personArray[2].getPersonID());
        System.out.println(persons[2].getPersonID());

        assertEquals(persons[2].getPersonID(),personArray[2].getPersonID());

    }

    @Test
    void getFamilyMembersFail() throws DataAccessError {
        Person[] personArray = null;
        pDao.insertPeople(persons);

        personArray = pDao.getFamilyMembers("Fake_USER");



        assertEquals(0,personArray.length);

    }

    @Test
    void removePeopleSuccess() throws DataAccessError {
        Event[] event = null;
        int numberOfPeopleRemoved = 0;

        String associatedUsername = bestPersonMaster.getAssociatedUsername();
        Person[] personArray = null;
        pDao.insertPeople(persons);

        numberOfPeopleRemoved = pDao.removePeople(associatedUsername);

        //System.out.println(numberOfPeopleRemoved);
        assertEquals(persons.length,numberOfPeopleRemoved);


    }

    @Test
    void removePeopleFail() throws DataAccessError {
        Event[] event = null;
        int numberOfPeopleRemoved = 0;

        String associatedUsername = "FAKE";
        Person[] personArray = null;
        pDao.insertPeople(persons);

        numberOfPeopleRemoved = pDao.removePeople(associatedUsername);

        //System.out.println(numberOfPeopleRemoved);
        assertEquals(0,numberOfPeopleRemoved);


    }

    @Test
    void insertPeopleSuccess() throws DataAccessError {
        pDao.insertPeople(persons);
        String username = bestPersonMaster.getAssociatedUsername();
      Person[] personTestArray = pDao.getFamilyMembers(username);

       // assertTrue(Arrays.equals(personTestArray,persons));
        assertEquals(personTestArray[0].getAssociatedUsername(),persons[0].getAssociatedUsername());

        assertEquals(personTestArray[0].getPersonID(),persons[0].getPersonID());

    }


    @Test
    void insertPeopleFailThrows()  {
        persons[0].setPersonID(null);
        assertThrows(DataAccessError.class,()-> pDao.insertPeople(persons));
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        conn.rollback();

    }


}
