package by.belous.contacts.service;

import by.belous.contacts.aspect.Transactional;
import by.belous.contacts.entity.Paging;
import by.belous.contacts.dao.mysql.ContactDAOException;
import by.belous.contacts.entity.Contact;
import by.belous.contacts.entity.ContactFilter;
import by.belous.contacts.entity.Photo;

import java.util.Calendar;
import java.util.List;

public interface ContactService {
    @Transactional
    void saveContact(Contact contact) throws ContactDAOException;

    @Transactional
    Contact getContact(Long id) throws ContactDAOException;

    @Transactional
    void deleteContact(Long[] contactsId) throws ContactDAOException;

    @Transactional
    void editContact(Long id, Contact contact) throws ContactDAOException;

    @Transactional
    List<Contact> getContacts(Paging paging) throws ContactDAOException;

    @Transactional
    List<Contact> composeContacts(Long[] contactsId) throws ContactDAOException;

    @Transactional
    List<Contact> searchContacts(ContactFilter contact) throws ContactDAOException;

    @Transactional
    List<Contact> getContactsByBirthday(Calendar date) throws ContactDAOException;

    @Transactional
    Photo getPhoto(long contactId) throws ContactDAOException;
}
