package by.belous.contacts.validator;

import by.belous.contacts.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();
    private List<String> errors;

    public ContactValidator() {
        this.errors = new ArrayList<>();
    }

    @Override
    public List<String> validate(Object obj) {
        Contact contact = (Contact) obj;
        if (contact != null) {
            String firstName = contact.getFirstName();
            String lastName = contact.getLastName();
            String middleName = contact.getMiddleName();
            Date birthday = contact.getDateOfBirth();
            String street = contact.getStreet();
            String house = contact.getHouse();
            String flat = contact.getFlat();
            Sex sex = contact.getGender();
            String citizenship = contact.getNationality();
            RelationshipStatus relationshipStatus = contact.getRelationshipStatus();
            String website = contact.getWebSite();
            String email = contact.getEmail();
            String currentJob = contact.getCurrentJob();
            if (StringUtils.isEmpty(firstName) || firstName.length() > 20) {
                errors.add("firstName must be not null and not empty, length must be less than 20");
            }
            if (StringUtils.isEmpty(lastName) || lastName.length() > 20) {
                errors.add("lastName must be not null and not empty, length must be less than 20");
            }
            if (StringUtils.isEmpty(middleName) || middleName.length() > 20) {
                errors.add("middleName must be not null and not empty, length must be less than 20");
            }
            if (birthday == null) {
                errors.add("birthday must be not null");
            }
            if (StringUtils.isEmpty(street) || street.length() > 30) {
                errors.add("street must be not null or not empty, length must be less than 30");
            }
            if (StringUtils.isEmpty(house) || house.length() > 7) {
                errors.add("house must be not null or not empty, length must be less than 7");
            }
            if (StringUtils.isEmpty(flat) || flat.length() > 5) {
                errors.add("flat must be not null or not empty, length must be less than 5");
            }
            if (sex == null) {
                errors.add("sex must be not null");
            }
            if (StringUtils.isEmpty(citizenship) || citizenship.length() > 20) {
                errors.add("citizenship must be not null and not empty, length must be less than 20");
            }

            if (relationshipStatus == null) {
                errors.add("relationshipStatus must be not null");
            }

            if (StringUtils.isEmpty(website) || website.length() > 255) {
                errors.add("website is not valid");
            }

            if (!emailValidator.isValid(email)) {
                errors.add("email is not valid");
            }

            if (StringUtils.isEmpty(currentJob) || currentJob.length() > 30) {
                errors.add("currentJob must be not null and not empty, length must be less than 20");
            }
            Photo photo = contact.getPhoto();
            if (photo != null) {
                String pathPhoto = photo.getName();
                if (StringUtils.isEmpty(pathPhoto) || pathPhoto.length() > 255) {
                    errors.add("pathPhoto must be not null and not empty, length must be less than 255");
                }
            }

            List<Phone> phones = contact.getPhones();
            if (CollectionUtils.isNotEmpty(phones)) {
                for (Phone phone : phones) {
                    Short countryCode = phone.getCountryCode();
                    Short operatorCode = phone.getOperatorCode();
                    Long number = phone.getPhoneNumber();
                    PhoneType phoneType = phone.getPhoneType();
                    String comment = phone.getComment();
                    if (countryCode == null || countryCode < 0 || countryCode > 65535) {
                        errors.add("countryCode must be not null, less than 65535 and greater than 0");
                    }
                    if (operatorCode == null || operatorCode < 0 || operatorCode > 65535) {
                        errors.add("operatorCode must be not null, less then 65535 and greater than 0");
                    }
                    if (number == null || number < 0 || number > 4294967295L) {
                        errors.add("operatorCode must be not null, less then 4294967295 and greater than 0");
                    }
                    if (phoneType == null) {
                        errors.add("phoneType must be not null");
                    }

                    if (StringUtils.isEmpty(comment) || comment.length() > 30) {
                        errors.add("comment must not be null or empty, length must be less than 30");
                    }
                }
            }
        }


        List<Attachment> attachments = contact.getAttachments();
        if (CollectionUtils.isNotEmpty(attachments)) {
            for (Attachment attachment : attachments) {
                String comment = attachment.getComment();
                String fileName = attachment.getFileName();
                if (StringUtils.isEmpty(comment) || comment.length() > 30) {
                    errors.add("comment must not be null or empty, length must be less than 30");
                }
                if (StringUtils.isEmpty(fileName) || fileName.length() > 80) {
                    errors.add("path must not be null or empty, length must be less than 80");
                }
            }
        }
        Location address = contact.getLocation();
        if (address != null) {
            String city = address.getCity();
            String country = address.getCountry();
            String zipCode = address.getZipCode();
            if (StringUtils.isEmpty(city) || city.length() > 20) {
                errors.add("city must be not null or not empty, length must be less than 20");
            }
            if (StringUtils.isEmpty(country) || country.length() > 20) {
                errors.add("country must be not null or not empty, length must be less than 20");
            }
            if (StringUtils.isEmpty(zipCode) || zipCode.length() > 15) {
                errors.add("zipCode must be not null or not empty, length must be less than 15");
            }
        } else {
            errors.add("address must be not null");
        }
        return errors;
    }
}
