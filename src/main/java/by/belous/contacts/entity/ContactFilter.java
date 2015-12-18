package by.belous.contacts.entity;

import java.util.Date;

public class ContactFilter {

    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateFrom;
    private Date dateTo;
    private Sex gender;
    private String nationality;
    private RelationshipStatus relationshipStatus;
    private String country;
    private String city;
    private Paging paging;

    public ContactFilter() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Sex getGender() {
        return gender;
    }

    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "ContactFilter{" +
                "firstName = " + firstName +
                ", lastName = " + lastName +
                ", middleName = " + middleName +
                ", dateFrom = " + dateFrom +
                ", dateTo = " + dateTo +
                ", gender = " + gender +
                ", relationshipStatus = " + relationshipStatus +
                ", country = " + country +
                ", city = " + city + '}';
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
