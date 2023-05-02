package Request_Response;

import model.Event;
import model.Person;
import model.User;

/**
 * Clears all data from the database (just like the /clear API)
 * Loads the user, person, and event data from the request body into the database.
 */
public class LoadRequest {
    /**
     * Array of users past by the load web API
     */
    User[] users = null;
    /**
     * Array of persons past by the load web API
     */
    Person[] persons = null;
    /**
     * Array of event past by the load web API
     */
    Event[] events = null;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public void printArrays()
    {
        System.out.println("Printing an array size");
       System.out.println(persons.length);
        System.out.println(events.length);
        System.out.println(users.length);

    }

    public User[] getUser() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
