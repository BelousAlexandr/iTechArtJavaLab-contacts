package by.belous.contacts.dao.mysql;

import by.belous.contacts.entity.Contact;
import by.belous.contacts.entity.Paging;
import by.belous.contacts.entity.Photo;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface ContactDAO {

    List<Contact> getContacts(Paging paging) throws ContactDAOException;

    Contact getContact(Long contactId) throws ContactDAOException;

    void edit(Long id, Contact contact) throws ContactDAOException;

    Long save(Contact contact) throws ContactDAOException;

    void deleteById(Long id) throws ContactDAOException;

    List<Contact> searchContacts(Map<String, Object> attributes) throws ContactDAOException;

    List<Contact> getContactsByBirthday(Calendar date) throws ContactDAOException;

    Photo getPhotoById(Long id) throws ContactDAOException;
}
