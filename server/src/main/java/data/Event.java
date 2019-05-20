package data;

public class Event extends DataUtil {
    private String eventType;
    private String eventID;
    private Double longitude;
    private Double latitude;
    private String city;
    private String country;
    private String year;
    private String descendant;
    private String personID; //personID belongs

    public Event(String eventType, Double longitude, Double latitude, String city, String country, String year, String descendant, String personID) {
        this.eventType = eventType;
        this.eventID = IDgenerator();
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.country = country;
        this.year = year;
        this.descendant = descendant;
        this.personID = personID;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
