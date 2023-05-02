package Service;

import DAO.DataAccessError;
import DAO.Database;
import DAO.PersonDAO;
import Request_Response.PersonRequest;
import Request_Response.PersonResponse;
import com.google.gson.Gson;
import model.Person;

import java.sql.Connection;

/**
 * This will carre out the webAPI of /person/ and its optional ID parameter
 */
public class PersonService {
    /**
     * This will either return a single person object or an array of person's object
     * @param personReq
     * @return This will retunr our response body for our http
     */
    PersonDAO personDAO = null;
    Database database = null;
    Gson gson = null;

    public PersonResponse person(PersonRequest personReq){
       Connection conn = null;
       gson = new Gson();
       String username = personReq.getAuthorizationToken().getUsername();
       Person[] familyMembers = null;
        try {
            database = Database.getInstance();
            conn = database.getConnection();
            personDAO = new PersonDAO(conn);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
        }

        String personID = null;
        PersonResponse personResponse = null;
        if(personReq.getOptionalParam())
        {
            personResponse = personResponseOptionaParameter(personReq.getPersonID(),username);
            return personResponse;
        }
        else{
        //no personID included below

            try {
                familyMembers = personDAO.getFamilyMembers(personReq.getAuthorizationToken().getUsername());
            } catch (DataAccessError dataAccessError) {
                dataAccessError.printStackTrace();
                personResponse = new PersonResponse("Error: "+dataAccessError.returnMessage());
                return personResponse;
            }

            personResponse = new PersonResponse(true,familyMembers);

        }


       return personResponse;
    }


    private PersonResponse personResponseOptionaParameter(String personID,String username)
    {   PersonResponse personResponse = null;
        Person person = null;


        try {
            person = personDAO.getPersonObject(personID);
            if(!username.equals(person.getAssociatedUsername()) && person.getAssociatedUsername() != null)
            {

                personResponse = new PersonResponse("Error: Requested person does not belong to this user");
                return personResponse;
            }
            if(person.getPersonID() == null)
            {
                personResponse = new PersonResponse("Error: Invalid PersonID parameter");
                return personResponse;
            }
            personResponse = new PersonResponse(person);
        } catch (DataAccessError dataAccessError) {
            dataAccessError.printStackTrace();
            personResponse = new PersonResponse("Error: "+dataAccessError.returnMessage());
            return personResponse;
        }




        return personResponse;
    }
}
