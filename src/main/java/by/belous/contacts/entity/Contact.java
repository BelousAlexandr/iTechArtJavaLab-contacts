package by.belous.contacts.entity;

import java.util.Date;
import java.util.List;

public class Contact {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;

    @SimpleDate
    private Date dateOfBirth;
    private Sex gender;
    private String nationality;
    private RelationshipStatus relationshipStatus;
    private String webSite;
    private String email;
    private String currentJob;
    private String street;
    private String house;
    private String flat;
    private Long locationId;
    @DomainObject
    private Location location;
    @DomainObject
    private List<Phone> phones;
    @DomainObject
    private Photo photo;
    @DomainObject
    private List<Attachment> attachments;

    public Contact() {
    }

    public Contact(Long id, String firstName, String lastName, String middleName, Date dateOfBirth, Sex gender,
                   String nationality, RelationshipStatus relationshipStatus, String webSite, String email,
                   String currentJob, String street, String house, String flat, Location location,
                   List<Phone> phones, Photo photo, List<Attachment> attachments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.relationshipStatus = relationshipStatus;
        this.webSite = webSite;
        this.email = email;
        this.currentJob = currentJob;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.location = location;
        this.phones = phones;
        this.photo = photo;
        this.attachments = attachments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName = " + firstName +
                ", lastName = " + lastName +
                ", middleName = " + middleName +
                ", dateOfBirth = " + dateOfBirth +
                ", gender = " + gender +
                ", nationality = " + nationality +
                ", relationshipStatus = " + relationshipStatus +
                ", webSite = " + webSite +
                ", email = " + email +
                ", currentJob = " + currentJob +
                ", location = " + location +
                ", phones = " + phones +
                ", photo = " + photo +
                ", attachments = " + attachments +
                '}';
    }

}
