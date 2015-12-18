package by.belous.contacts.dao.mysql;

import by.belous.contacts.entity.Phone;

import java.util.List;

public interface PhoneDAO {
    void save(Phone phone) throws ContactDAOException;

    List<Phone> getPhonesByContactId(Long id) throws ContactDAOException;

    void edit(Phone phone) throws ContactDAOException;

    void deleteById(Long contactId) throws ContactDAOException;

    void deleteByContactId(Long id) throws ContactDAOException;
}
