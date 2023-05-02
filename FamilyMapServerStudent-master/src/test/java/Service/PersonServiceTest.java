package Service;

import DAO.*;
import Request_Response.EventRequest;
import Request_Response.EventResponse;
import Request_Response.PersonRequest;
import Request_Response.PersonResponse;
import model.Event;
import model.Person;
import model.authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonServiceTest {
    Person person;
    PersonResponse personResponse;
    PersonRequest personRequest;
    PersonService personService;
    PersonDAO personDAO;
    Database database;
    Connection conn;

    @BeforeEach
    void Setup() throws DataAccessError {
        personRequest = new PersonRequest();
        personService = new PersonService();
        personResponse = new PersonResponse();

        database = Database.getInstance();
        conn = database.getConnection();
        personDAO = new PersonDAO(conn);
        personDAO.clear();
        AuthtokenDAO authtokenDAO = new AuthtokenDAO(conn);
        authtokenDAO.createToken(new authtoken("54321","hiya"));


        personDAO.insertPerson(new Person("123456","hiya","theOne","Jonas","m","897","2864","098734"));





    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();


    }

    @Test
    void eventSuccess()
    {
        authtoken authtoken = new authtoken("54321","hiya");
        personRequest.setPersonID("123456");
        personRequest.setOptionalParam(true);
        personRequest.setAuthorizationToken(authtoken);

        personResponse = personService.person(personRequest);

        assertTrue(personResponse.getSuccess());


    }

    @Test
    void eventFail()
    {
        authtoken authtoken = new authtoken("54321","hiya");
       //authtoken does not exist
        personRequest.setPersonID("12345");
        personRequest.setOptionalParam(true);
        personRequest.setAuthorizationToken(authtoken);

        personResponse = personService.person(personRequest);

        assertFalse(personResponse.getSuccess());


    }

}
