package model;

import java.util.Objects;

public class Person {
    /**
     * The person that was generate's ID
     */
    private String personID;
    /**
     * The username associated with this person
     */
    private String associatedUsername;
    /**
     * the first name of this generated person
     */
    private String firstName;
    /**
     * the last name of the generated person
     */
    private String lastName;
    /**
     * The gender of the generated person, only two values M or F user enumerator to ensure only two options
     */
    private String gender; //use enumerator for just f or m characters/strings
    /**
     * The ID of this persons Father / or NULL
     */
    private String fatherID; //can be null
    /**
     * The ID of this persons Mother / or NULL
     */
    private String motherID; //can be null
    /**
     * The ID of this persons Spouse / or NULL
     */
    private String spouseID; //can be null

    /**
     * This creates a person object with ID's from father mother and spouse. Look below for more info on
     * other properties
     *
     * @param personID This person's ID
     * @param associatedUsername The assocaited username of this person
     * @param firstName This persons first name
     * @param lastName This persons last name
     * @param gender This persons gender (Can only have two types male or female) use ENUMERATOR
     * @param fatherID The ID of this persons father or NULL
     * @param motherID The ID of this persons mother or NULL
     * @param spouseID he ID of this persons Spouse or NULL
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * Another equals object to see if this person exists, we will potentially avoid duplicates
     * @param o generic object which will be cast person potentially
     * @return will return false of object is not equal to another
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) && associatedUsername.equals(person.associatedUsername) && firstName.equals(person.firstName) && lastName.equals(person.lastName) && gender.equals(person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }

    /**
     * possible hashcode generated to store this person object in a collection
     * @return will return a hashcode to use for a hashtable
     */

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }
}
