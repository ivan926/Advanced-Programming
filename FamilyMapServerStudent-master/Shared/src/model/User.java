package model;

import java.util.Objects;

public class User {
    /**
     * The users username
     */
    private String username = null;
    /**
     * the users current password
     */
    private String password = null;
    /**
     * the Users email address
     */
    private String email = null;
    /**
     * the Users first name
     */
    private String firstName = null;
    /**
     * the users lastName
     */
    private String lastName = null;
    /**
     * the users gender, can only have two options, either female or male
     */
    private String gender = null;
    /**
     * the users personID
     */
    private String personID  = null;

    /**
     * The user object created along with parameters discuessed above
     * @param username The users username
     * @param password The users password
     * @param email the users email address
     * @param firstName the users given name
     * @param lastName the users last name
     * @param gender the users gender f or m
     * @param personID the users personID
     */
    public User(String username,String password,String email,String firstName,String lastName,String gender,String personID)
    {
         this.username = username;
         this.password = password;
         this.email = email;
         this.firstName = firstName;
         this.lastName = lastName;
         this.gender = gender;
         this.personID = personID;

    }

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Test constructor when creating random user
     * @param navoos
     * @param larkaino
     */
    public User(String navoos, String larkaino) {
        this.username = navoos;
        this.password = larkaino;
        this.email = "Noice@gmail.com";
        this.firstName = "lucas";
        this.lastName = "Ravioli";
        this.gender = "m";
        this.personID = "DefiniUnique";
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * equals to verif that no other users with the same details exists
     * @param o generic object to potentially be cast
     * @return returns false if object does not equal another.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender) && Objects.equals(personID, user.personID);
    }
}
