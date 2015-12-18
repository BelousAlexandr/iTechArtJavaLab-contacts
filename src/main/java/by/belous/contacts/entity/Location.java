package by.belous.contacts.entity;

public class Location {
    private Long locationId;
    private String country;
    private String city;
    private String zipCode;
    public Location(){}

    public Location(Long locationId, String country, String zipCode, String city) {
        this.locationId = locationId;
        this.country = country;
        this.zipCode = zipCode;
        this.city = city;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "country: " + country +
                ", city: " + city +
                ", zipCode: " + zipCode;
    }
}
