package Service;

import DAO.*;
import Request_Response.FillRequest;
import Request_Response.FillResponse;
import Request_Response.RegisterResponse;
import model.Event;
import model.Person;
import model.User;
import model.authtoken;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This will execute the /fill/ webAPI for the specified user
 */
public class FillService {
    /**
     * This will fill in a certain amount of generations of ancestors, either specifically from the user
     * or 4 by default, as long as the username is registered. If data exists, it is deleted
     * @param fillReq this is our request Body with properties needed to manipulate our database
     * @return returns our response body for our http response
     */

    public FillResponse fill(FillRequest fillReq){

        System.out.println("\ninside the Fill service method");
        fillReq.stringify();
        Connection conn = null;
        try {
            Database database = Database.getInstance();
            conn = database.getConnection();

        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        UserDAO userDAO = new UserDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);
        User currentUser = null;

        System.out.println("Verifying that the user is registered");

        try {
            currentUser = userDAO.getUserById(fillReq.getUsername());
            if(currentUser.getUsername() == null)
            {
                FillResponse fillResponse = new FillResponse("Error: Invalid Username",false);
                return fillResponse;
            }


        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
            FillResponse fillResponse = new FillResponse("Error:" + dataAccessError.getMessage(),false);
            return fillResponse;
        }
        //check to see if user already has data assocaited with him
        Event[] eventArray = null;
        Person[] personArray = null;
        try {
            eventArray = eventDAO.findForUser(currentUser.getUsername());
            personArray = personDAO.getFamilyMembers(currentUser.getUsername());

            if(eventArray.length > 0)
            {
                eventDAO.removeAllUserEvents(currentUser.getUsername());
            }

            if(personArray.length > 0)
            {
                personDAO.removePeople(currentUser.getUsername());
            }

            conn.commit();

        } catch (DataAccessError | SQLException dataAccessError) {
            System.out.println(dataAccessError.getMessage() );
            dataAccessError.printStackTrace();
        }



        FamilyTree treeGenerator = new FamilyTree(currentUser,conn);
        //negative value
        if(fillReq.getGenerations() < 0)
        {
            FillResponse fillResponse = new FillResponse("Error: Invalid generation parameter",false);
            return fillResponse;
        }

        //default 4 generations no parameter
        if(fillReq.getGenerations() == 4)
        {
            treeGenerator.fillTree();

        }
        else
        {
            treeGenerator.fillTree(fillReq.getGenerations());
        }
        //getting number of persons and events added for the user
        try {
            eventArray = eventDAO.findForUser(currentUser.getUsername());
            personArray = personDAO.getFamilyMembers(currentUser.getUsername());
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        String message = "Successfully added " + personArray.length + " persons and " +
                eventArray.length + " events to the database";
        FillResponse fillResponse = new FillResponse(message,true);
//        database.closeConnection(true);
        try {
            conn.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return fillResponse;



    }
}
