package by.belous.contacts.entity;

public class Phone {
    private Long phoneId;
    private Short countryCode;
    private Short operatorCode;
    private Long phoneNumber;
    private PhoneType phoneType;
    private String comment;
    private Boolean deleted;
    private Boolean edited;
    private Long contactId;
    private Boolean hasInDB;

    public Boolean getHasInDB() {
        return hasInDB;
    }

    public void setHasInDB(Boolean hasInDB) {
        this.hasInDB = hasInDB;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Short getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Short countryCode) {
        this.countryCode = countryCode;
    }

    public Short getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Short operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneId = " + phoneId +
                ", countryCode = " + countryCode +
                ", operatorCode = " + operatorCode +
                ", phoneNumber = " + phoneNumber +
                ", phoneType = " + phoneType +
                ", comment = " + comment +
                ", deleted = " + deleted +
                ", contactId = " + contactId +
                '}';
    }

    /*public static void main(String[] args) {
        Pattern pattern = Pattern.compile("/contacts/search/$");
        Matcher matcher = pattern.matcher("/contacts/search/");
        System.out.println(matcher.matches());
    }*/
}
