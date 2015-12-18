package by.belous.contacts.dao.mysql;

import by.belous.contacts.dao.mysql.util.ContactDAOUtil;
import by.belous.contacts.entity.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDAOImpl extends AbstractDAO implements LocationDAO {

    Logger logger = LoggerFactory.getLogger(LocationDAOImpl.class);

    @Override
    public Location getLocationById(Long id) throws ContactDAOException {
        String query = "select * from location as l where l.location_id=?";
        logger.debug(query + " location_id: " + id);
        ResultSet rs = executeQuery(query, id);
        return getLocation(rs);
    }

    @Override
    public Location getLocationByZipCode(String zipCode) throws ContactDAOException {
        String query = "select * from location as l where l.zip_code=?";
        logger.debug(query + " zip_code: " + zipCode);
        ResultSet rs = executeQuery(query, zipCode);
        return getLocation(rs);
    }

    private Location getLocation(ResultSet rs) throws ContactDAOException {
        Location location = new Location();
        try {
            if (rs.next()) {
                Long location_id = rs.getLong("location_id");
                String zipCode = rs.getString("zip_code");
                String city = rs.getString("city");
                String country = rs.getString("country");
                location.setLocationId(location_id);
                location.setCity(city);
                location.setCountry(country);
                location.setZipCode(zipCode);
            }
        } catch (SQLException e) {
            throw new ContactDAOException("Error occurred while fetching the registered location types", e);
        } finally {
            ContactDAOUtil.cleanupResources(rs);
        }
        return location;
    }
}
