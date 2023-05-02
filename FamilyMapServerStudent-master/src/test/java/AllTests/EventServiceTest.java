package AllTests;

import DAO.AuthtokenDAO;
import DAO.DataAccessError;
import DAO.Database;
import DAO.EventDAO;
import Request_Response.EventRequest;
import Request_Response.EventResponse;
import Service.EventService;
import model.Event;
import model.authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventServiceTest {
    Event event;
    EventResponse eventResponse;
    EventRequest eventRequest;
    EventService eventService;
    EventDAO eventDAO;
    Database database;
    Connection conn;

    @BeforeEach
    void Setup() throws DataAccessError {
        eventRequest = new EventRequest();
        eventService = new EventService();
        eventResponse = new EventResponse();

        database = Database.getInstance();
        conn = database.getConnection();
        eventDAO = new EventDAO(conn);
        eventDAO.clear();
        AuthtokenDAO authtokenDAO = new AuthtokenDAO(conn);
        authtokenDAO.createToken(new authtoken("54321","hiya"));


        eventDAO.insertEvent(new Event("12345","hiya","54321",(float)12.3,(float)45.78,"Hiyakm","Lirana","Baboom",2345));





    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();


    }

    @Test
    void eventSuccess()
    {
        authtoken authtoken = new authtoken("54321","hiya");
        eventRequest.setEventID("12345");
        eventRequest.setOptionalParam(true);
        eventRequest.setAuthorizationToken(authtoken);

      eventResponse = eventService.event(eventRequest);

      assertTrue(eventResponse.getSuccess());


    }

    @Test
    void eventFail()
    {
        authtoken authtoken = new authtoken("54321","hiya");
        eventRequest.setEventID("1234");
        eventRequest.setOptionalParam(true);
        eventRequest.setAuthorizationToken(authtoken);

        eventResponse = eventService.event(eventRequest);

        assertFalse(eventResponse.getSuccess());


    }






}
