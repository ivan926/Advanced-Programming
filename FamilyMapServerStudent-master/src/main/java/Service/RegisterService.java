package Service;

import DAO.*;
import JSON.*;
import Request_Response.RegisterRequest;
import Request_Response.RegisterResponse;
import model.User;
import model.authtoken;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * THis will carry out the /user/register webAPI
 */
public class RegisterService {

    Database database;

    /**
     * Executes the web api called by the user in this case to register a new user
     * @param regReq this is the RegisterRequest object that will have the details we need to register the new user
     * @return This returns the response body for the HTTP response
     */
    public RegisterResponse register(RegisterRequest regReq){
        System.out.println("\ninside the register method");
        regReq.stringify();
        System.out.println("New person ID is being created for the user");
        UniqueID ID_Database = UniqueID.getUniqueDatabaseInstance();
        User newUser = new User(regReq.getUsername(), regReq.getPassword(), regReq.getEmail(), regReq.getFirstName(), regReq.getLastName(), regReq.getGender(),ID_Database.getUniqueID());
//        System.out.println(newPersonID.toString());
//        newPersonID = UUID.randomUUID();
//        System.out.println(newPersonID.toString());
        Connection conn = null;
        Database database = null;

        try {
            database = Database.getInstance();
            conn = database.getConnection();
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }
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







//            conn = database.getConnection();
//            conn = database.getConnection();

            System.out.println(conn);
            System.out.println("Passing database connection to UserDAO");
            userdao = new UserDAO(conn);

            if(userdao.isDuplicate(newUser.getUsername()))
            {
                RegisterResponse registerResponse = new RegisterResponse("Error: Username already taken");
                System.out.println("Returning HTTP response java OBJECT");
                return registerResponse;
            }
            System.out.println("registering new user...");
            userdao.createUser(newUser);

            System.out.println("Generating User Family Tree");
            FamilyTree tree = new FamilyTree(newUser,conn);
            tree.fillTree();
            //generateFourGenerations(newUser,conn);

            userdao.validate(newUser.getUsername(), newUser.getPassword());


            authtokenModelObject = new authtoken(ID_Database.getUniqueID(), newUser.getUsername());
            System.out.println("passing database connection to AUTHTOKEN_DAO");
            authtokendao = new AuthtokenDAO(conn);
            System.out.println("passing authtoken model object to create token");
            authTokenForUser = authtokendao.createToken(authtokenModelObject);
            System.out.println("AuthToken created and returned " + authTokenForUser);

//


        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();

            System.out.println("Something went wrong when trying to open data base Connection");
            RegisterResponse registerResponse = new RegisterResponse("Error: Something went wrong with the connection");
            System.out.println("Returning HTTP response java OBJECT");
            return registerResponse;


        }

//        database.closeConnection(true);
        try {
            conn.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


        RegisterResponse regResponse = new RegisterResponse(authTokenForUser,newUser.getUsername(),newUser.getPersonID());

        return regResponse;


    }


}
