package DAO;

import model.Event;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class interfaces with the database, it can query, update, remove, or drop
 */
public class EventDAO extends DAO{
    /*
           Query data
           update
           insert
           delete
     */

    private Connection conn;

    public EventDAO(Connection conn)
    {
        this.conn = conn;

    }
    /**
     * This will insert new rows into event table
     * @param event Event model class object with attributes that will match table columns
     * @throws DataAccessError exception
     */
    public int insertEvent(Event event)throws DataAccessError
    {
        String SQL = "INSERT INTO Event (eventID,associatedUsername,PersonID,latitude," +
                "longitude,country,city,eventType,year) VALUES(?,?,?,?,?,?,?,?,?)";
        Float latitude = event.getLatitude();
        Float longitude = event.getLongitude();
        Integer year = event.getYear();
        int rows = 0;
        try(PreparedStatement stmnt = conn.prepareStatement(SQL))
        {
            stmnt.setString(1,event.getEventID());
            stmnt.setString(2,event.getAssociatedUsername());
            stmnt.setString(3,event.getPersonID());
            stmnt.setFloat(4,latitude);
            stmnt.setFloat(5,longitude);
            stmnt.setString(6,event.getCountry());
            stmnt.setString(7,event.getCity());
            stmnt.setString(8,event.getEventType());
            stmnt.setInt(9,year);

            rows = stmnt.executeUpdate();

        }
        catch(SQLException exception)
        {
            System.out.println("Could not insert an event");
            exception.printStackTrace();
        }

        return rows;
    }

    /**
     * Will (query) find an event based on event ID in event table
     * @param eventID The event ID correlates to primary key in database table
     //* @param authtoken auth token needed to retrieve an event
     * @return event object with current eventID.
     * @throws DataAccessError exception
     */
    public Event findEvent(String eventID)throws DataAccessError{

        String sql = "SELECT * FROM Event WHERE eventID = ?";
        Event event = null;

        try(PreparedStatement stmnt = conn.prepareStatement(sql))
        {
            stmnt.setString(1,eventID);

            ResultSet rs = stmnt.executeQuery();

            event = new Event(rs.getString(1),rs.getString(2),rs.getString(3), rs.getFloat(4),rs.getFloat(5),rs.getString(6),rs.getString(7), rs.getString(8),rs.getInt(9));


        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }


        return event;
    }


    /**
     * We are deleting(clearing) all rows
     * @throws DataAccessError exception
     */
    @Override
    public int clear()throws DataAccessError{

        int count = 0;

        String sql = "delete from Event";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {

            count = stmt.executeUpdate();


            System.out.printf("Deleted %d Events\n", count);
        } catch (SQLException throwables) {
            throw new DataAccessError("Could not delete from User table");
        }

        return count;
    }



    public int removeAllUserEvents(String associatedUsername)throws DataAccessError{
        String sql = "DELETE FROM Event WHERE associatedUsername = ? ";
        int allEventsRemoved = 0;
        try (PreparedStatement stmnt = conn.prepareStatement(sql)){

            stmnt.setString(1,associatedUsername);

            allEventsRemoved = stmnt.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return allEventsRemoved;
    }



    /**
     * This class will return a list of all events for this user
     * @param username This will be the users username
     * @return This will return a primitive array of events from the events table
     * @throws DataAccessError exception
     */
    public Event[] findForUser(String username)throws DataAccessError{
        String sql = "SELECT * FROM Event WHERE associatedUsername = ? ";

        Event[] eventArray = null;
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

            eventArray = new Event[totalPeople];

            //reset pointer to top of the data table row
            rs =  stmnt.executeQuery();

            while(rs.next())
            {
                eventArray[arrayIndex] = (     new Event(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getFloat(4),rs.getFloat(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getInt(9)));



                arrayIndex++;
            }


        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }

        return eventArray;

    }

    /**
     * This will receive a list of events, this is used in conjunction with the load function
     * @param listOfEvents array of event objects
     * @throws DataAccessError
     */
    public void insertEvents(Event[] listOfEvents)throws DataAccessError{


        int sizeofList = listOfEvents.length;
        int arrayIndex = 0;
        Event event;

        System.out.println(sizeofList);
        while(arrayIndex < sizeofList)
        {

            String EVENT_ID = listOfEvents[arrayIndex].getEventID();
            String ASSOCIATED_USERNAME = listOfEvents[arrayIndex].getAssociatedUsername();
            String PERSONID = listOfEvents[arrayIndex].getPersonID();
            Float LATITUDE = listOfEvents[arrayIndex].getLatitude();
            Float LONGITUDE = listOfEvents[arrayIndex].getLongitude();
            String COUNTRY = listOfEvents[arrayIndex].getCountry();
            String CITY = listOfEvents[arrayIndex].getCity();
            String EVENT_TYPE = listOfEvents[arrayIndex].getEventType();
            Integer YEAR = listOfEvents[arrayIndex].getYear();

            event = new Event(EVENT_ID,ASSOCIATED_USERNAME,PERSONID,LATITUDE,LONGITUDE,COUNTRY,CITY,EVENT_TYPE,YEAR);

            String SQL = "INSERT INTO Event (eventID,associatedUsername,PersonID,latitude," +
                    "longitude,country,city,eventType,year) VALUES(?,?,?,?,?,?,?,?,?)";

            try(PreparedStatement stmnt = conn.prepareStatement(SQL))
            {
                stmnt.setString(1,event.getEventID());
                stmnt.setString(2,event.getAssociatedUsername());
                stmnt.setString(3,event.getPersonID());
                stmnt.setFloat(4,LATITUDE);
                stmnt.setFloat(5,event.getLongitude());
                stmnt.setString(6,event.getCountry());
                stmnt.setString(7,event.getCity());
                stmnt.setString(8,event.getEventType());
                stmnt.setInt(9,event.getYear());

                stmnt.executeUpdate();



            }
            catch(SQLException exception)
            {   exception.printStackTrace();
                System.out.println("Could not insert an event");
                System.out.println(exception.getMessage());
                throw new DataAccessError(exception.getErrorCode(), exception.getMessage());

            }
            catch(NullPointerException exception)
            {
                //19 is the error code for constraints
                throw new DataAccessError(19, exception.getMessage());

            }

            arrayIndex++;


        }




    }





}
