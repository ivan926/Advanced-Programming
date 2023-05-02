package AllTests;

import DAO.AuthtokenDAO;
import DAO.DataAccessError;
import DAO.Database;
import DAO.UserDAO;
import Request_Response.LoadRequest;
import Request_Response.LoadResponse;
import Service.LoadService;
import com.google.gson.Gson;
import model.User;
import model.authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    LoadService loadService;
    LoadResponse loadResponse;
    LoadRequest loadRequest;
    Database database;
    Connection conn;
    AuthtokenDAO authtokenDAO;
    UserDAO userDAO;
    model.authtoken authtoken;
    Gson gson;



    @BeforeEach
    void setup() throws DataAccessError {

        loadService = new LoadService();
        gson = new Gson();
        database = Database.getInstance();
        conn = database.getConnection();
        userDAO = new UserDAO(conn);
        authtokenDAO = new AuthtokenDAO(conn);
        authtoken = new authtoken("HelloThere","Brightside");
        userDAO.clear();
        authtokenDAO.clear();
        userDAO.createUser(new User("Linus","Tovardo"));

        authtokenDAO.createToken(authtoken);





    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();

    }

    @Test
    void LoadServiceSuccess()
    {
        String jsonRequestBody = "{" +
                "\"users\":[ {" +
                "         \"username\":\"sheila\"," +
                "         \"password\":\"parker\"," +
                "         \"email\":\"sheila@parker.com\"," +
                "         \"firstName\":\"Sheila\"," +
                "         \"lastName\":\"Parker\"," +
                "         \"gender\":\"f\"," +
                "         \"personID\":\"Sheila_Parker\"" +
                "      }" +
                "" +
                "]," +
                "\"persons\":[ {" +
                "         \"firstName\":\"Sheila\"," +
                "         \"lastName\":\"Parker\"," +
                "         \"gender\":\"f\"," +
                "         \"personID\":\"Sheila_Parker\"," +
                "         \"spouseID\":\"Davis_Hyer\"," +
                "         \"fatherID\":\"Blaine_McGary\"," +
                "         \"motherID\":\"Betty_White\"," +
                "         \"associatedUsername\":\"sheila\"" +
                "      }" +
                "" +
                "]," +
                "\"events\":[  {" +
                "\"eventType\":\"completed asteroids\"," +
                "\"personID\":\"Sheila_Parker\"," +
                "\"city\":\"Qaanaaq\"," +
                "\"country\":\"Denmark\"," +
                "\"latitude\":77.4667," +
                "\"longitude\":-68.7667," +
                "\"year\":2014," +
                "\"eventID\":\"Sheila_Asteroids\"," +
                "\"associatedUsername\":\"sheila\"" +
                "}" +
                "" +
                "]" +
                "}";


        String SuccessMessage = "{" +
                "\"message\":\"Successfully added 1 users," +
                " 1 persons," +
                " and 1 events to the database\"," +
                "\"success\":true" +
                "}";
        loadRequest = gson.fromJson(jsonRequestBody,LoadRequest.class);
       loadResponse = loadService.load(loadRequest);
       String json = gson.toJson(loadResponse);
        System.out.println(json);
       assertTrue(loadResponse.getSuccess());
       assertEquals(SuccessMessage,json);



    }


    @Test
    void LoadServiceFailure()
    {
        String jsonRequestBody = "{" +
                "\"users\":[ {" +
                "         " +
                "         \"password\":\"parker\"," +
                "         \"email\":\"sheila@parker.com\"," +
                "         \"firstName\":\"Sheila\"," +
                "         \"lastName\":\"Parker\"," +
                "         \"gender\":\"f\"," +
                "         \"personID\":\"Sheila_Parker\"" +
                "      }" +
                "" +
                "]," +
                "\"persons\":[ {" +
                "         \"firstName\":\"Sheila\"," +
                "         \"lastName\":\"Parker\"," +
                "         \"gender\":\"f\"," +
                "         \"personID\":\"Sheila_Parker\"," +
                "         \"spouseID\":\"Davis_Hyer\"," +
                "         \"fatherID\":\"Blaine_McGary\"," +
                "         \"motherID\":\"Betty_White\"," +
                "         \"associatedUsername\":\"sheila\"" +
                "      }" +
                "" +
                "]," +
                "\"events\":[  {" +
                "\"eventType\":\"completed asteroids\"," +
                "\"personID\":\"Sheila_Parker\"," +
                "\"city\":\"Qaanaaq\"," +
                "\"country\":\"Denmark\"," +
                "\"latitude\":77.4667," +
                "\"longitude\":-68.7667," +
                "\"year\":2014," +
                "\"eventID\":\"Sheila_Asteroids\"," +
                "\"associatedUsername\":\"sheila\"" +
                "}" +
                "" +
                "]" +
                "}";


        String ErrorMessage = "{" +
                "\"message\":\"Successfully added 1 users," +
                " 1 persons," +
                " and 1 events to the database\"," +
                "\"success\":true" +
                "}";
        loadRequest = gson.fromJson(jsonRequestBody,LoadRequest.class);
        loadResponse = loadService.load(loadRequest);
        String json = gson.toJson(loadResponse);
        System.out.println(json);
        assertFalse(loadResponse.getSuccess());
        //assertEquals(SuccessMessage,json);



    }






}
