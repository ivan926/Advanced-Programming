package Request_Response;

import model.Person;

/**
 * Response body will have a success content or error
 */
public class PersonResponse {

    /**
     * Prints out the response body
     */
    public void printRequest(){}

    /**
     * The users associated name to the person
     */
    private String associatedUsername;
    /**
     * the person ID of this person
     */
    private String personID;
    /**
     * first name of the person
     */
    private String firstName;
    /**
     * last name of the person
     */
    private String lastName;
    /**
     * gender of the person either F or M
     */
    private String gender;
    /**
     * the persons father ID
     */
    private String fatherID = null;
    /**
     * the persons mother ID
     */
    private String motherID = null;
    /**
     * the person spouse ID
     */
    private String spouseID = null;
    /**
     * success boolean value that will be true or false
     */
    private Boolean success;

    /**
     * the potential message
     */
    private String message;

    /**
     * constructor that will intialize the array of persons if there is no parameter
     * @param success
     * @param data array of person objects
     */
    public PersonResponse(Boolean success, Person[] data) {
        this.success = success;
        this.data = data;
    }

    /**
     * array of persons
     */
    private Person[] data;

    /**
     * initializes values
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     * @param success
     */
    public PersonResponse(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, Boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    /**
     * This constructor represents the error response body
     * @param message The error message
     */
    public PersonResponse(String message) {
        this.success = false;
        this.message = message;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
