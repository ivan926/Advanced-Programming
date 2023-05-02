package Service;

import DAO.AuthtokenDAO;
import DAO.DataAccessError;
import DAO.Database;
import DAO.UserDAO;
import Request_Response.LoginRequest;
import Request_Response.LoginResponse;
import model.User;
import model.authtoken;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This will carry out the /user/login webAPI
 */
public class LoginService {
    /**
     * This will log the user in and return an auth token
     * @param logReq this contains our request body with the properties needed to log the user in
     * @return returns the response body for our http response
     */
    public LoginResponse login(LoginRequest logReq){
        logReq.stringify();
        UniqueID uniqueID = UniqueID.getUniqueDatabaseInstance();
        String AuthTokenID = null;
        Connection conn = null;
        try {
            Database database = Database.getInstance();
            conn = database.getConnection();
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        AuthtokenDAO authtokenDAO = new AuthtokenDAO(conn);

        LoginResponse loginResponse = null;
        UserDAO userDAO = new UserDAO(conn);
        Boolean validated = false;
        try {
            validated = userDAO.validate(logReq.getUsername(),logReq.getPassword());

            if(validated)
            {
                AuthTokenID = uniqueID.getUniqueID();
                authtoken AuthorizationTok = new authtoken(AuthTokenID,logReq.getUsername());
                authtokenDAO.createToken(AuthorizationTok);
                User currentUser = userDAO.getUserById(AuthorizationTok.getUsername());
                loginResponse = new LoginResponse(AuthorizationTok.getAuthToken(),AuthorizationTok.getUsername(),currentUser.getPersonID());

                conn.commit();
            }
            else
            {
                loginResponse = new LoginResponse("Error Invalid Value");

                return loginResponse;
            }

        } catch (DataAccessError | SQLException dataAccessError) {
            dataAccessError.printStackTrace();
        }


        return loginResponse;
    }
}
