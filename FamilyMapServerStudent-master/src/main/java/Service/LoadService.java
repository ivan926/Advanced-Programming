package Service;

import DAO.*;
import Request_Response.LoadRequest;
import Request_Response.LoadResponse;
import com.google.gson.Gson;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This will execute the /load webAPI
 */
public class LoadService {
    /**
     * This will clear all data from the database and then populate the database with appropriate user,
     * person, and event information.
     * @param loadReq Will contain the necessary req data with possible array of user, person, and event data
     * @return will return the response body which will contain a message and the success boolean value
     */
    UserDAO userDAO = null;
    PersonDAO personDAO = null;
    EventDAO eventDAO =null;
    Database database = null;

    public LoadResponse load(LoadRequest loadReq){

        try {
            database  = Database.getInstance();
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }
        Connection conn = database.getConnection();

        userDAO = new UserDAO(conn);
        personDAO = new PersonDAO(conn);
        eventDAO = new EventDAO(conn);

        //clear the database
        ArrayList<DAO> arrayOfDAOs = new ArrayList<>();
        arrayOfDAOs.add(userDAO);
        arrayOfDAOs.add(personDAO);
        arrayOfDAOs.add(eventDAO);

        System.out.println("Clearing database");
        clearDatabase(arrayOfDAOs);


        //loads arrays into database schemas
        System.out.println("Inserting arrays");
        LoadResponse loadResponse = insertArrays(loadReq);
        //committing changes
        try {

            conn.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return loadResponse;

    }

    private LoadResponse insertArrays(LoadRequest loadRequest)
    {   Gson gson = new Gson();
        Person[] personArray = loadRequest.getPersons();
        User[] userArray = loadRequest.getUser();
        Event[] eventArray = loadRequest.getEvents();

        int sizeOfArray = 0;
        int arrayIndex = 0;
        sizeOfArray = userArray.length;

        System.out.println("Inserting user arrays");
        while(arrayIndex < sizeOfArray)
        {
            String username = userArray[arrayIndex].getUsername();
            String password = userArray[arrayIndex].getPassword();
            String email = userArray[arrayIndex].getEmail();
            String firstName = userArray[arrayIndex].getFirstName();
            String lastName = userArray[arrayIndex].getLastName();
            String gender = userArray[arrayIndex].getGender();
            String personID = userArray[arrayIndex].getPersonID();
            User user = new User(username,password,email,firstName,lastName,gender,personID);

            try {
                userDAO.createUser(user);
            } catch (DataAccessError dataAccessError) {
                if(dataAccessError.returnErrorCode() == 19)
                {
                    return new LoadResponse("Error: " + dataAccessError.returnMessage(),false);

                }
                dataAccessError.printStackTrace();
            }
            arrayIndex++;
        }

        System.out.println("Successfully added list of users");

        sizeOfArray = personArray.length;
        arrayIndex = 0;

        try {
            personDAO.insertPeople(personArray);
            System.out.println("Successfully added list of people");
            eventDAO.insertEvents(eventArray);
            //database.closeConnection(true);
            System.out.println("Successfully added a list of events");
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
            if(dataAccessError.returnErrorCode() == 19)
            {
                return new LoadResponse(dataAccessError.returnMessage(),false);

            }
        }

//        while(arrayIndex < sizeOfArray)
//        {
//            String username = userArray[arrayIndex].getUsername();
//            String password = userArray[arrayIndex].getPassword();
//            String email = userArray[arrayIndex].getEmail();
//            String firstName = userArray[arrayIndex].getFirstName();
//            String lastName = userArray[arrayIndex].getLastName();
//            String gender = userArray[arrayIndex].getGender();
//            String personID = userArray[arrayIndex].getPersonID();
//            User user = new User(username,password,email,firstName,lastName,gender,personID);
//
//            try {
//                personDAO.insertPeople();
//            } catch (DataAccessError dataAccessError) {
//                dataAccessError.printStackTrace();
//            }
//
//        }

        return new LoadResponse("Successfully added " + userArray.length + " users, " + personArray.length + " persons, and " + eventArray.length + " events to the database",true);
    }

    private void clearDatabase(ArrayList<DAO> arrayOfDAOs)
    {

        int index = 0;
        try {
            while(index <  arrayOfDAOs.size())
            {

                arrayOfDAOs.get(index).clear();

                index++;
            }

        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }


    }

}
