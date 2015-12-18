package by.belous.contacts.dao.mysql;

import by.belous.contacts.entity.Location;

public interface LocationDAO {

    Location getLocationById(Long id) throws ContactDAOException;

    Location getLocationByZipCode(String zipCode) throws ContactDAOException;
}
