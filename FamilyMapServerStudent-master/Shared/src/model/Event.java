package model;

import java.util.Objects;

public class Event {
    /**
     * The unique event ID
     */
    private String eventID = null;
    /**
     * The username of the user who is associated with the event
     */
    private String associatedUsername = null;
    /**
     * The ID of the person
     */
    private String personID = null;
    /**
     * the float value of our maps latitude
     */
    private Float latitude = null;
    /**
     * the float value of our longitude
     */
    private Float longitude = null;
    /**
     * the country that the event took place
     */
    private String country = null;
    /**
     * city which event took place
     */
    private String city = null;
    /**
     * the type of event that occured ,baptism, marriage etc
     */
    private String eventType = null;
    /**
     * the year the even took place
     */
    private Integer year = null;

    /**
     * Constructs an event
     * @param eventID The evenID
     * @param username The userName of the person in question
     * @param personID The persons ID
     * @param latitude The latitude marks a geographical location
     * @param longitude The longitude marks a geographical location
     * @param country The country the event took place
     * @param city The city which the even took place
     * @param eventType What type of event was it
     * @param year The year the even took place
     */
    public Event(String eventID, String username, String personID, Float latitude, Float longitude,
                 String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Equals which tries to find a similar event which took place
     * @param o takes in a generic parent object type does cast into event type after conditions are met
     * @return returns false if the object are not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }
}
