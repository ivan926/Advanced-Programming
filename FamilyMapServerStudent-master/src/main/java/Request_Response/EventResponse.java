package Request_Response;

/**
 * Returns potential response or error of the event request
 *
 */
public class EventResponse {

    /**
     * Prints out the response body
     */
    public void printRequest(){}

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
    private float latitude;
    /**
     * the longitude of the event
     */
    private float longitude;
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
    private int year;
    /**
     * the boolean value that states if the request was a success
     */
    private Boolean success;
    /**
     * the potential error message
     */
    private String message;

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
     * @param success
     */
    public EventResponse(String associatedUsername, String eventID, float latitude, float longitude, String country, String city, String eventType, int year, Boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }

    /**
     * the error response value initialization;
     * @param success
     * @param message
     */

    public EventResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
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

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
