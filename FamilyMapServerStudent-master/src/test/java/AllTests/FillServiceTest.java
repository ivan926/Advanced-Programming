package AllTests;

import DAO.DataAccessError;
import DAO.Database;
import DAO.UserDAO;
import Request_Response.FillRequest;
import Request_Response.FillResponse;
import Service.FillService;
import Service.RegisterService;
import com.google.gson.Gson;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FillServiceTest {
    private FillService fillService;
    private FillRequest fillRequest;
    private FillResponse fillResponse;
    private UserDAO userDAO;
    private Connection conn;
    private Database database;
    private Gson gson;
    private RegisterService registerService;

    @BeforeEach
    void setup() throws DataAccessError {
        database = Database.getInstance();
        conn = database.getConnection();
        gson = new Gson();

        fillService = new FillService();
        fillRequest = new FillRequest();

        userDAO = new UserDAO(conn);




        userDAO.clear();


    }

    @Test
    void FillserviceSuccess() throws DataAccessError {
        String responseMessage = "{" +
                "\"message\":\"Successfully added 31 persons and 91 events to the database\"," +
                "\"success\":true" +
                "}";
        //userDAO.clear();
        userDAO.createUser(new User("narkin","Larkaino"));

        fillRequest.setGenerations(4);
        fillRequest.setUsername("narkin");

        fillResponse = fillService.fill(fillRequest);

        String json = gson.toJson(fillResponse);

        assertEquals(responseMessage,json);


    }

    @Test
    void FillserviceFail() throws DataAccessError {

        String responseMessage = "{" +
                "\"message\":\"Successfully added 31 persons and 91 events to the database\"," +
                "\"success\":true" +
                "}";
        userDAO.createUser(new User("narkin","Larkaino"));
        fillRequest.setUsername("narkin");
        fillRequest.setGenerations(-1);
        fillResponse = fillService.fill(fillRequest);

        String json = gson.toJson(fillResponse);
        //negative value will not work
        assertFalse(fillResponse.getSuccess());


    }

    @AfterEach()
    void destructor() throws SQLException {
        conn.rollback();


    }
}
