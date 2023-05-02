package Request_Response;

import model.Event;

/**
 * Returns potential response or error of the event request
 *
 */
public class EventResponse {


    public EventResponse() {

    }

    /**
     * Prints out the response body
     */
    public void printRequest(){}
    /**
     * Event array
     */
    private Event[] data;

    /**
     * the assocaited username with this event
     */
    private String associatedUsername;
    /**
     * the event Identifier
     */
    private String eventID;
    /**
     * the latitude of the event
     */
    private Float latitude;
    /**
     * the longitude of the event
     */
    private Float longitude;
    /**
     * country the event took place
     */
    private String country;
    /**
     * the city the event took place
     */
    private String city;
    /**
     * the type of the event
     */
    private String eventType;
    /**
     * the year the event took place
     */
    private Integer year;
    /**
     * the boolean value that states if the request was a success
     */
    private Boolean success;
    /**
     * the potential error message
     */
    private String message;

    private String personID;

    /**
     * the success response body value assignments
     * @param associatedUsername
     * @param eventID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public EventResponse(String associatedUsername, String eventID, float latitude, float longitude, String country, String city, String eventType, int year, String PersonID) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.personID = PersonID;
        success = true;

    }

    public EventResponse(Event event) {
        this.associatedUsername = event.getAssociatedUsername();
        this.personID = event.getPersonID();
        this.eventID = event.getEventID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
        success = true;


    }

    public EventResponse(Event[] event) {
       success = true;
       this.data = event;
    }

    /**
     * the error response value initialization;
     * @param success
     * @param message
     */

    public EventResponse( String message,Boolean success) {
        this.success = success;
        this.message = message;
    }

    public Event[] getEventData()
    {
        return this.data;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    public String getPersonID() {
        return personID;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
