package Service;

import DAO.DataAccessError;
import DAO.Database;
import DAO.EventDAO;
import Request_Response.EventRequest;
import Request_Response.EventResponse;
import com.google.gson.Gson;
import model.Event;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This will execute the /event webAPI
 */
public class EventService {
    /**
     * This will return either a single event object or multiple
     * @param eventReq
     * @return This will return our success body or error body response
     */


    EventDAO eventDAO = null;
    Database database = null;
    Gson gson = null;
    public EventResponse event(EventRequest eventReq){



        Connection conn = null;
        gson = new Gson();
        Event[] events = null;
        String username = eventReq.getAuthorizationToken().getUsername();
        try {
            database = Database.getInstance();
            conn = database.getConnection();
            eventDAO = new EventDAO(conn);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        String personID = null;
        EventResponse eventResponse = null;
        if(eventReq.getOptionalParam())
        {
            eventResponse = EventResponseOptionaParameter(eventReq.getEventID(),username);
            return eventResponse;
        }
        else{
            //no personID included below

            try {
                events = eventDAO.findForUser(eventReq.getAuthorizationToken().getUsername());
            } catch (DataAccessError dataAccessError) {
                dataAccessError.printStackTrace();
                eventResponse = new EventResponse("Error: "+dataAccessError.returnMessage(),false);
                return eventResponse;
            }

            eventResponse = new EventResponse(events);

        }


        return eventResponse;
    }


    private EventResponse EventResponseOptionaParameter(String personID,String username)
    {   EventResponse eventResponse = null;
        Event event = null;


        try {
            event = eventDAO.findEvent(personID);
            if(!username.equals(event.getAssociatedUsername()) && event.getAssociatedUsername() != null)
            {
                eventResponse = new EventResponse("Error: Requested event does not belong to this user",false);
                return eventResponse;
            }
            if(event.getEventID() == null)
            {

                eventResponse = new EventResponse("Error: Invalid eventID",false);
                return eventResponse;
            }
            eventResponse = new EventResponse(event);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
            eventResponse = new EventResponse("Error: "+dataAccessError.returnMessage(),false);
            return eventResponse;

        }



        return eventResponse;
    }

}
