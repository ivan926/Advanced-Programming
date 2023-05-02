package DAO;

import DAO.AuthtokenDAO;
import model.authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class authtokenDAOTest {

    AuthtokenDAO authtokenDAO;
    Database database;
    model.authtoken authtoken;
    Connection conn;

    @BeforeEach
    void setup()
    {
        try {
            database = Database.getInstance();
            conn = database.getConnection();

        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        authtokenDAO = new AuthtokenDAO(conn);


        authtoken = new authtoken("Navina","Stormwind");


    }

    @AfterEach
    void clean() throws SQLException {
        conn.rollback();


    }


    @Test
    private void createTokenSuccess()
    {

        String authToken = null;
        try {
            authToken = authtokenDAO.createToken(authtoken);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }
        assertEquals(authtoken.getAuthToken(),authToken);

        authtoken.setAuthToken("567");
        authtoken.setUsername("link");

        try {
            authToken = authtokenDAO.createToken(authtoken);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        assertEquals("567",authToken);



    }

    @Test
    private void createTokenThrowDataAccessError()
    {
        try {
            authtokenDAO.createToken(authtoken);
            authtokenDAO.createToken(authtoken);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        assertThrows(DataAccessError.class,()-> authtokenDAO.createToken(authtoken) );


    }

    @Test
    void getAuthTokenSuccess()
    {   String authToken = null;
        try {
            authtokenDAO.createToken(authtoken);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }
        authToken = authtoken.getAuthToken();

        model.authtoken authtoken1 = null;

        try {
            authtoken1 = authtokenDAO.getAuthToken(authToken);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        assertEquals(authtoken,authtoken1);



    }

    @Test
    void getAuthTokenErrorNotFound()
    {   String authToken = null;
        try {
            authtokenDAO.createToken(authtoken);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }
        authToken = "DOESNOTEXIST";


        String finalAuthToken = authToken;


        try {
            model.authtoken authtoken1 = authtokenDAO.getAuthToken(finalAuthToken);
            assertNull(authtoken1.getAuthToken());
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }
        //assertThrows(DataAccessError.class,() -> authtokenDAO.getAuthToken(finalAuthToken));




    }

    @Test
    void clearTest() throws DataAccessError
    {
        //wipe any tokens currently in the databse
        authtokenDAO.clear();
        int numberOfTokensDeleted = 0;
        authtokenDAO.createToken(authtoken);
        authtoken.setUsername("Heller");
        authtoken.setAuthToken("SECRET");
        authtokenDAO.createToken(authtoken);

        numberOfTokensDeleted = authtokenDAO.clear();

        assertEquals(2,numberOfTokensDeleted);


    }






}
