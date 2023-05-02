package Service;

import DAO.*;
import Request_Response.ClearRequest;
import Request_Response.ClearResponse;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This clears the data from all the databases using the /clear webAPI
 */
public class ClearService {
    /**
     * this request will delete all data in the data base
     * @param clearReq contains information to know clear request desires to be executed
     * @return returns our success or response body for the clear function
     */
    public ClearResponse clear(ClearRequest clearReq){
        Connection conn = null;
        try {
            Database database = Database.getInstance();
            conn = database.getConnection();
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        UserDAO userDAO = new UserDAO(conn);
        EventDAO eventDAO = new EventDAO(conn);
        PersonDAO personDAO = new PersonDAO(conn);
        AuthtokenDAO authtokenDAO = new AuthtokenDAO(conn);

        try {
            userDAO.clear();
            eventDAO.clear();
            personDAO.clear();
            authtokenDAO.clear();
            conn.commit();
        } catch (DataAccessError dataAccessError) {

            ClearResponse clearResponse = new ClearResponse("Error: " + dataAccessError.getMessage(),true);
            dataAccessError.printStackTrace();
            return clearResponse;
        }
        catch( SQLException sqlException)
        {
            ClearResponse clearResponse = new ClearResponse("Error: " + sqlException.getMessage(),true);
            sqlException.printStackTrace();
            return clearResponse;

        }

        ClearResponse clearResponse = new ClearResponse("Clear succeeded.",true);

        return clearResponse;
    }
}
