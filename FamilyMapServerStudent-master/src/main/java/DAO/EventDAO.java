package DAO;

import model.Event;

/**
 * This class interfaces with the database, it can query, update, remove, or drop
 */
public class EventDAO {
    /*
           Query data
           update
           insert
           delete
     */

    /**
     * This will insert new rows into event table
     * @param event Event model class object with attributes that will match table columns
     * @throws DataAccessError exception
     */
    public void insertEvent(Event event)throws DataAccessError{}

    /**
     * Will (query) find an event based on event ID in event table
     * @param eventID The event ID correlates to primary key in database table
     //* @param authtoken auth token needed to retrieve an event
     * @return event object with current eventID.
     * @throws DataAccessError exception
     */
    public Event findEvent(String eventID)throws DataAccessError{return null;}

    /**
     * finds the events of all family members of the user
     * @param username we will use the username to find all family members
     //* @param authtoken auth token needed to retrieve an event
     * @return a primitive array of all events
     * @throws DataAccessError exception
     */
    public Event[] findAll(String username)throws DataAccessError{return null;}


    /**
     * We are deleting(clearing) all rows
     * @throws DataAccessError exception
     */
    public void clear()throws DataAccessError{}

    /**
     * We are removing specified event using the eventID
     * @param eventID The row that the user wishes to remove using some sort of identifier
     * @throws DataAccessError exception
     */
    public void removeEvent(String eventID)throws DataAccessError{}

    /**
     * We will update a table, by editing values within the rows
     * @param event we will pass through an object that is a model of the database and update the results
     *  so we know the current data in memory
     *  @throws DataAccessError exception
     */
    public void update(Event event)throws DataAccessError{}

    /**
     * This class will return a list of all events for this user
     * @param username This will be the users username
     * @return This will return a primitive array of events from the events table
     * @throws DataAccessError exception
     */
    public Event[] findForUser(String username)throws DataAccessError{return null;}

    /**
     * This will receive a list of events, this is used in conjunction with the load function
     * @param listOfEvents array of event objects
     * @throws DataAccessError
     */
    public void insertEvents(Event[] listOfEvents)throws DataAccessError{}





}
