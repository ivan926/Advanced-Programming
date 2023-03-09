package Service;

import DAO.*;
import Request_Response.RegisterRequest;
import Request_Response.RegisterResponse;
import model.User;
import model.authtoken;

import java.sql.Connection;
import java.util.Locale;
import java.util.UUID;

/**
 * THis will carry out the /user/register webAPI
 */
public class RegisterService {

    /**
     * Executes the web api called by the user in this case to register a new user
     * @param regReq this is the RegisterRequest object that will have the details we need to register the new user
     * @return This returns the response body for the HTTP response
     */
    public RegisterResponse register(RegisterRequest regReq){
        System.out.println("\ninside the register method");
        regReq.stringify();
        System.out.println("New person ID is being created for the user");
        UUID newPersonID = UUID.randomUUID();
        User newUser = new User(regReq.getUsername(), regReq.getPassword(), regReq.getEmail(), regReq.getFirstName(), regReq.getLastName(), regReq.getGender(),newPersonID.toString());
//        System.out.println(newPersonID.toString());
//        newPersonID = UUID.randomUUID();
//        System.out.println(newPersonID.toString());
        Connection conn;
        Database database;
        UserDAO userdao;
        AuthtokenDAO authtokendao;
        authtoken authtokenModelObject;
        String authTokenForUser = null;
        String message;

        System.out.println("attempting database connection");

        try {

            if(newUser.getUsername() == null || newUser.getPassword() == null || newUser.getEmail() == null
            || newUser.getFirstName() == null || newUser.getLastName() == null || newUser.getGender() == null)
            {

                message = "Error: Request Property missing";
                RegisterResponse registerResponse = new RegisterResponse(message);
                return registerResponse;

            }

            if( !newUser.getGender().equalsIgnoreCase("f") && !(newUser.getGender().equalsIgnoreCase("m")))
            {

                message = "Error: Invalid value";
                RegisterResponse registerResponse = new RegisterResponse(message);
                return registerResponse;
            }

            database = Database.getInstance();
            System.out.println("Got an instance of the Database");
            conn = database.getConnection();
            System.out.println("Passing database connection to UserDAO");
            userdao = new UserDAO(conn);
            System.out.println("registering new user...");
            userdao.createUser(newUser);

            generateFourGenerations(newUser,conn);

            userdao.validate(newUser.getUsername(), newUser.getPassword());

            newPersonID = UUID.randomUUID();
            authtokenModelObject = new authtoken(newPersonID.toString(), newUser.getUsername());
            System.out.println("passing database connection to AUTHTOKEN_DAO");
            authtokendao = new AuthtokenDAO(conn);
            System.out.println("passing authtoken model object to create token");
            authTokenForUser = authTokenForUser = authtokendao.createToken(authtokenModelObject);
            System.out.println("AuthToken created and returned " + authTokenForUser);
            database.closeConnection(true);


        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
            System.out.println("Something went wrong when trying to open data base Connection");
            RegisterResponse registerResponse = new RegisterResponse("Error: Username already taken");
            return registerResponse;


        }


        RegisterResponse regResponse = new RegisterResponse(authTokenForUser,newUser.getUsername(),newUser.getPersonID());

        return regResponse;


    }

    void generateFourGenerations(User user,Connection conn)
    {
        PersonDAO person = new PersonDAO(conn);

        System.out.println("Filled 4 generations");

    }

}
