package DAO;

import model.Event;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest {

    EventDAO eventDAO;
    Event eventMaster;
    Event eventChild1;
    Event eventChild2;
    Event event2;
    Event[] events;
    Database database;
    Connection conn;

    @BeforeEach
    void setup()
    {
        try {
            database = Database.getInstance();
            conn = database.getConnection();
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        eventMaster = new Event("Marriage_ID","navis","myMan",(float)456.67,(float)5676.7,"Nazca",
                "Lima","marriage",2006);

        eventChild1 = new Event("Snow_Balling","navis","myMan",(float)456.67,(float)5676.7,"Nazca",
                "Netcong","Actvity",1993);

        eventChild2 = new Event("Riding_bicycle","navis","myMan",(float)456.67,(float)5676.7,"Nazca",
                "Morristown","riding",1999);

        events = new Event[]{eventMaster, eventChild1, eventChild2};


        event2 = new Event("Lumber_Jacking","incran","larson45",(float)4578.67,(float)76.7,"Madagascar",
                "Sao Paulo","Wood cutting",2011);
        eventDAO = new EventDAO(conn);


    }


    @Test
    void insertEvent() throws DataAccessError {
        int eventsAdded = 0;

        eventsAdded+= eventDAO.insertEvent(eventMaster);
        eventsAdded+= eventDAO.insertEvent(event2);

        assertEquals(2,eventsAdded);



    }


    @Test
    void insertEventFail() throws DataAccessError {
        int eventsAdded = 0;


        eventsAdded += eventDAO.insertEvent(eventMaster);
        eventsAdded += eventDAO.insertEvent(eventMaster);
        //Unique ID should only be added once
        assertEquals(1,eventsAdded);

    }

    @Test
    void findEventSuccess() throws DataAccessError {
        String eventID = "Marriage_ID";
        eventDAO.insertEvent(eventMaster);
        Event eventCopy = eventDAO.findEvent(eventID);

        assertEquals(eventMaster,eventCopy);

    }

    @Test
    void findEventFail() throws DataAccessError {
        String eventID = "ID_DOES_NOT_EXIST";
        eventDAO.insertEvent(eventMaster);

        //should return an even full of properties that are null
        Event eventCopy = eventDAO.findEvent(eventID);

        assertNull(eventCopy.getEventType());

    }

    @Test
    void clear() throws DataAccessError {
        int rowsCleared = 0;
        eventDAO.clear();
        eventDAO.insertEvent(eventMaster);
        eventDAO.insertEvent(event2);

        rowsCleared = eventDAO.clear();

        assertEquals(2,rowsCleared);


    }

    @Test
    void removeAllUserEventsSuccess() throws DataAccessError {
        int rowsRemoved = 0;
        eventDAO.insertEvents(events);

        rowsRemoved = eventDAO.removeAllUserEvents(eventMaster.getAssociatedUsername());

        assertEquals(3,rowsRemoved);


    }

    @Test
    void removeAllUserEventsUsernameDoesNotExist() throws DataAccessError {
        int rowsRemoved = 0;
        eventDAO.insertEvents(events);

        rowsRemoved = eventDAO.removeAllUserEvents("FAKE");

        assertEquals(0,rowsRemoved);


    }

    @Test
    void findForUserSuccess() throws DataAccessError {
        int rowsRemoved = 0;
        String username = eventMaster.getAssociatedUsername();
        eventDAO.insertEvents(events);
        eventDAO.insertEvent(event2);

        Event[] testEvents = null;
        testEvents = eventDAO.findForUser(username);
        //uses Arrays helper static method equals
        //to verify that both arrays have the same value
        assertTrue(Arrays.equals(testEvents,events));



    }

    @Test
    void findForUserFail() throws DataAccessError {
        int rowsRemoved = 0;

        eventDAO.insertEvents(events);
        eventDAO.insertEvent(event2);

        Event[] testEvents = null;
        //should return an empty array
        testEvents = eventDAO.findForUser("DOES_NOT_EXIST");

        //checks length of array and matches to expected value
        assertEquals(0,testEvents.length);



    }

    @Test
    void insertEventsSuccess() throws DataAccessError {
        int rowsRemoved = 0;
        //inserting array of events
        eventDAO.insertEvents(events);
        eventDAO.insertEvent(event2);

        String associatedUsername1 = null;
        String eventID = null;

        //inserting events with different usernames
        associatedUsername1 = eventMaster.getAssociatedUsername();
        eventID = event2.getEventID();

        Event[] testEvents = null;
        testEvents = eventDAO.findForUser(associatedUsername1);

        //checks that the events just inserted are equal to each other
        assertTrue(Arrays.equals(events,testEvents));

        //checks lone event put is the same
        Event testEvent = null;
        testEvent = eventDAO.findEvent(eventID);
        assertEquals(event2,testEvent);




    }

    @Test
    void insertEventsFailThrows() throws DataAccessError {
        int rowsRemoved = 0;
        //inserting array of events
        //making sure one of the arrays has an invalid value null
        events[0].setEventType(null);

        //needs to throw exception related to not null
        assertThrows(DataAccessError.class,()-> eventDAO.insertEvents(events));

    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();

    }



}
