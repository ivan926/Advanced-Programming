package JSON;

public class Location {
    String country;
    String city;
    String latitude;
    String longitude;

    public Location(String country, String city, String latitude, String longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        String locationData = "Country =  " + country + "\nCity = " + city +
                "\nLatitude = " + latitude + "\nLongitude = " + longitude + "\n";

        return locationData;
    }
}
