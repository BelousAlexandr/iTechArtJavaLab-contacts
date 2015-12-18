package by.belous.contacts.service;

import by.belous.contacts.aspect.Transactional;
import by.belous.contacts.dao.mysql.*;
import by.belous.contacts.entity.*;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ContactServiceImpl implements ContactService {

    private Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private ContactDAO contactDAO;
    private PhoneDAO phoneDAO;
    private LocationDAO locationDAO;
    private AttachmentDAO attachmentDAO;

    public ContactServiceImpl() {
        contactDAO = new ContactDAOImpl();
        phoneDAO = new PhoneDAOImpl();
        locationDAO = new LocationDAOImpl();
        attachmentDAO = new AttachmentDAOImpl();
    }

    @Override
    @Transactional
    public void saveContact(Contact contact) throws ContactDAOException {
        Location location = locationDAO.getLocationByZipCode(contact.getLocation().getZipCode());
        contact.setLocation(location);
        contact.setLocationId(location.getLocationId());
        Long contactId = contactDAO.save(contact);
        logger.info("save contact: " + contact);

        List<Phone> phones = contact.getPhones();
        for (Phone phone : phones) {
            phone.setContactId(contactId);
            phoneDAO.save(phone);
            logger.info("save phone: " + phone);
        }

        List<Attachment> attachments = contact.getAttachments();
        for (Attachment attachment : attachments) {
            attachment.setContactId(contactId);
            attachmentDAO.save(attachment);
            logger.info("save attachment: " + attachment);
        }
    }

    @Override
    @Transactional
    public Contact getContact(Long id) throws ContactDAOException {
        List<Phone> phones = phoneDAO.getPhonesByContactId(id);
        List<Attachment> attachments = attachmentDAO.getAttachments(id);
        Contact contact = contactDAO.getContact(id);
        Long locationId = contact.getLocationId();
        Location location = locationDAO.getLocationById(locationId);
        location.setLocationId(locationId);
        contact.setId(id);
        contact.setPhones(phones);
        contact.setAttachments(attachments);
        contact.setLocation(location);
        return contact;
    }

    @Override
    @Transactional
    public void deleteContact(Long[] ids) throws ContactDAOException {
        for (Long id : ids) {
            logger.info("delete attacmentById: " + id);
            attachmentDAO.deleteByContactId(id);
            logger.info("delete phoneByContactId:" + id);
            phoneDAO.deleteByContactId(id);
            logger.info("delete contactById:: " + id);
            contactDAO.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void editContact(Long id, Contact contact) throws ContactDAOException {
        Location location = locationDAO.getLocationByZipCode(contact.getLocation().getZipCode());
        contact.setLocationId(location.getLocationId());
        List<Attachment> attachments = contact.getAttachments();
        for (Attachment attachment : attachments) {
            attachment.setContactId(id);
            if (attachment.getDeleted()) {
                logger.info("delete attacmentById: " + id);
                attachmentDAO.deleteById(attachment.getAttachmentId());
                continue;
            }
            if (attachment.getEdited()) {
                logger.info("edit attacment: " + attachment);
                attachmentDAO.edit(attachment);
            } else if (!attachment.getHasInDB() && !attachment.getDeleted() && !attachment.getEdited()) {
                logger.info("save attacment: " + attachment);
                attachmentDAO.save(attachment);
            }

        }
        List<Phone> phones = contact.getPhones();
        for (Phone phone : phones) {
            phone.setContactId(id);
            if (phone.getDeleted()) {
                logger.info("delete phoneById: " + id);
                phoneDAO.deleteById(phone.getPhoneId());
                continue;
            }
            if (phone.getEdited()) {
                logger.info("edit phone: " + phone);
                phoneDAO.edit(phone);
            } else if (!phone.getHasInDB() && !phone.getDeleted() && !phone.getEdited()) {
                logger.info("save phone: " + phone);
                phoneDAO.save(phone);
            }
        }
        contactDAO.edit(id, contact);
    }

    @Override
    @Transactional
    public List<Contact> getContacts(Paging paging) throws ContactDAOException {
        return contactDAO.getContacts(paging);
    }

    @Override
    @Transactional
    public List<Contact> composeContacts(Long[] contactsId) throws ContactDAOException {
        List<Contact> listComposeContacts = new ArrayList<>();
        for (Long id : contactsId) {
            Contact contact = contactDAO.getContact(id);
            logger.info("get contact: " + contact);
            listComposeContacts.add(contact);
        }
        return listComposeContacts;
    }

    @Override
    @Transactional
    public List<Contact> searchContacts(ContactFilter contact) throws ContactDAOException {
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String middleName = contact.getMiddleName();
        Date dateFrom = contact.getDateFrom();
        Date dateTo = contact.getDateTo();
        Sex gender = contact.getGender();
        String nationality = contact.getNationality();
        RelationshipStatus relationshipStatus = contact.getRelationshipStatus();
        String country = contact.getCountry();
        String city = contact.getCity();
        Map<String, Object> attributes = new HashMap<>();
        addNotNullAttribute(firstName, attributes, "firstName");
        addNotNullAttribute(lastName, attributes, "lastName");
        addNotNullAttribute(middleName, attributes, "middleName");
        addNotNullAttribute(dateFrom, attributes, "dateFrom");
        addNotNullAttribute(dateTo, attributes, "dateTo");
        if (gender != null) {
            attributes.put("gender", gender.name());
        }
        addNotNullAttribute(nationality, attributes, "nationality");
        if (relationshipStatus != null) {
            attributes.put("relationshipStatus", relationshipStatus.name());
        }
        addNotNullAttribute(country, attributes, "country");
        addNotNullAttribute(city, attributes, "city");
        if (MapUtils.isNotEmpty(attributes)) {
            return contactDAO.searchContacts(attributes);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public List<Contact> getContactsByBirthday(Calendar date) throws ContactDAOException {
        return contactDAO.getContactsByBirthday(date);
    }

    @Transactional
    @Override
    public Photo getPhoto(long contactId) throws ContactDAOException {
        return contactDAO.getPhotoById(contactId);
    }

    private void addNotNullAttribute(Object obj, Map<String, Object> attributes, String name) {
        if (obj != null) {
            attributes.put(name, obj);
        }
    }
}
