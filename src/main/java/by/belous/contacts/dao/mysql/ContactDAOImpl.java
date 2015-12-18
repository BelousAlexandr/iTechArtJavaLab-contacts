package by.belous.contacts.dao.mysql;

import by.belous.contacts.ThreadContext;
import by.belous.contacts.controller.QueryParam;
import by.belous.contacts.dao.mysql.util.ContactDAOUtil;
import by.belous.contacts.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ContactDAOImpl extends AbstractDAO implements ContactDAO {

    private Logger logger = LoggerFactory.getLogger(ContactDAOImpl.class);

    @Override
    public List<Contact> getContacts(@QueryParam Paging paging) throws ContactDAOException {
        String query = "select SQL_CALC_FOUND_ROWS c.contact_id, c.first_name, c.last_name, c.middle_name, c.birthday, " +
                "c.current_job, c.street, c.house, c.flat, l.zip_code, l.city, l.country from contact as c INNER JOIN " +
                "location as l ON c.location_id=l.location_id where c.isDeleted=0 LIMIT ?, ?";
        logger.debug(query);
        List<Contact> contacts  = execute(query,  paging, (paging.getPage() - 1) * paging.getPageSize(), paging.getPageSize());
        logger.debug("return contacts: " + contacts);
        return contacts;
    }

    @Override
    public Contact getContact(Long contactId) throws ContactDAOException {
        String query = "select c.first_name, c.last_name, c.middle_name, c.birthday, c.gender, c.nationality," +
                "c.relationship_status, c.web_site, c.email, c.current_job, c.photo_path, c.street, c.house, " +
                "c.flat, c.location_id from contact as c WHERE c.isDeleted=0 AND c.contact_id=?";
        logger.debug(query + "contactID: " + contactId);
        ResultSet rs = executeQuery(query, contactId);
        Contact contact = getContact(rs);
        logger.debug("return contact: " + contact);
        return contact;
    }

    @Override
    public void edit(Long id, Contact contact) throws ContactDAOException {
        String query = "update contact set first_name=?, last_name=?, middle_name=?, birthday=?, gender=?, " +
                "nationality=?, relationship_status=?, web_site=?, email=?, current_job=?, street=?, house=?, flat=? ";
        String where = "where contact.contact_id=?";
        logger.debug(query + "id: " + id + " contact: " + contact);
        if (contact.getPhoto() == null) {
            query = query + where;
            executeUpdate(query, contact.getFirstName(), contact.getLastName(), contact.getMiddleName(),
                    contact.getDateOfBirth(), contact.getGender().name(), contact.getNationality(),
                    contact.getRelationshipStatus().name(), contact.getWebSite(), contact.getEmail(),
                    contact.getCurrentJob(), contact.getStreet(), contact.getHouse(),
                    contact.getFlat(), id);
        } else {
            query = query + ", photo_path=?" + where;
            executeUpdate(query, contact.getFirstName(), contact.getLastName(), contact.getMiddleName(),
                    contact.getDateOfBirth(), contact.getGender().name(), contact.getNationality(),
                    contact.getRelationshipStatus().name(), contact.getWebSite(), contact.getEmail(),
                    contact.getCurrentJob(), contact.getStreet(), contact.getHouse(),
                    contact.getFlat(), contact.getPhoto().getName(), id);
        }
    }

    @Override
    public Long save(Contact contact) throws ContactDAOException {
        String query;
        if (contact.getPhoto() == null) {
            query = "insert into contact (first_name, last_name, middle_name, birthday, gender, " +
                    "nationality, relationship_status, web_site, email, current_job, street, house, flat, " +
                    "location_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return executeUpdate(query, contact.getFirstName(), contact.getLastName(), contact.getMiddleName(),
                    contact.getDateOfBirth(), contact.getGender().name(), contact.getNationality(),
                    contact.getRelationshipStatus().name(), contact.getWebSite(), contact.getEmail(),
                    contact.getCurrentJob(), contact.getStreet(), contact.getHouse(),
                    contact.getFlat(), contact.getLocationId());
        } else {
            query = "insert into contact (first_name, last_name, middle_name, birthday, gender, " +
                    "nationality, relationship_status, web_site, email, current_job, photo_path, " +
                    "street, house, flat, location_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return executeUpdate(query, contact.getFirstName(), contact.getLastName(), contact.getMiddleName(),
                    contact.getDateOfBirth(), contact.getGender().name(), contact.getNationality(),
                    contact.getRelationshipStatus().name(), contact.getWebSite(), contact.getEmail(),
                    contact.getCurrentJob(), contact.getPhoto().getName(), contact.getStreet(), contact.getHouse(),
                    contact.getFlat(), contact.getLocationId());
        }
    }

    @Override
    public void deleteById(Long id) throws ContactDAOException {
        String query = "update contact set contact.isDeleted=1 where contact.contact_id=?";
        logger.debug(query + " contact_id: " + id);
        executeUpdate(query, id);
    }

    @Override
    public List<Contact> searchContacts(Map<String, Object> attributes) throws ContactDAOException {
        StringBuilder sb = new StringBuilder();
        List<Object> values = new ArrayList<>();
        for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
            String key = attribute.getKey();
            switch (key) {
                case "dateFrom":
                    sb.append("AND c.birthday > ? ");
                    break;
                case "dateTo":
                    sb.append("AND c.birthday < ? ");
                    break;
                case "gender":
                    sb.append("AND c.gender=? ");
                    break;
                case "relationshipStatus":
                    sb.append("AND c.relationship_status=? ");
                    break;
                case "firstName":
                    sb.append("AND c.first_name LIKE ? ");
                    break;
                case "lastName":
                    sb.append("AND c.last_name LIKE ? ");
                    break;
                case "nationality":
                    sb.append("AND c.nationality=?");
                    break;
                case "middleName":
                    sb.append("AND c.middle_name LIKE ? ");
                    break;
                default:
                    sb.append("AND ").append("l.").append(key).append("=? ");
                    break;
            }
            if (key.equals("firstName") || key.equals("lastName") || key.equals("middleName")) {
                values.add(attribute.getValue() + "%");
            } else {
                values.add(attribute.getValue());
            }
        }

        String query = "select c.contact_id, c.first_name, c.last_name, c.middle_name, c.birthday, c.current_job, " +
                "c.street, c.house, c.flat, l.zip_code, l.city, l.country from contact as c " +
                "INNER JOIN location as l ON c.location_id=l.location_id where c.isDeleted=0 " + sb.toString();
        logger.debug(query + "values: " + Arrays.toString(values.toArray()));
        ResultSet rs = executeQuery(query, values.toArray());
        return getContacts(rs);
    }

    @Override
    public List<Contact> getContactsByBirthday(Calendar date) throws ContactDAOException {
        String query = "select first_name, last_name, middle_name, email from contact where " +
                "MONTH(birthday)=? AND DAY(birthday)=?";
        logger.debug(query + "date: " + date);
        ResultSet resultSet = executeQuery(query, date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH));
        List<Contact> contactsByBirthday = getContactsByBirthday(resultSet);
        logger.debug("result contactsByBirthday: " + contactsByBirthday);
        return contactsByBirthday;
    }

    @Override
    public Photo getPhotoById(Long id) throws ContactDAOException {
        String query = "select photo_path from contact where contact.contact_id=?";
        ResultSet rs = executeQuery(query, id);
        return getPhoto(rs);
    }

    private Photo getPhoto(ResultSet rs) throws ContactDAOException {
        Photo photo = new Photo();
        try {
            if (rs.next()) {
                String photoPath = rs.getString("photo_path");
                photo.setName(photoPath);
            }
        } catch (SQLException e) {
            throw new ContactDAOException("Error occurred while fetching the registered photo types", e);
        } finally {
            ContactDAOUtil.cleanupResources(rs);
        }
        return photo;
    }


    private List<Contact> getContactsByBirthday(ResultSet rs) throws ContactDAOException {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Contact contact = new Contact();
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String middleName = rs.getString("middle_name");
                    String email = rs.getString("email");
                    contact.setFirstName(firstName);
                    contact.setLastName(lastName);
                    contact.setMiddleName(middleName);
                    contact.setEmail(email);
                    contacts.add(contact);
                }
            }
        } catch (SQLException e) {
            throw new ContactDAOException("Error occurred while fetching the registered contact types", e);
        } finally {
            ContactDAOUtil.cleanupResources(rs);
        }
        return contacts;
    }

    private List<Contact> getContacts(ResultSet rs) throws ContactDAOException {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            if (!rs.wasNull()) {
                while (rs.next()) {
                    Long id = rs.getLong("contact_id");
                    String name = rs.getString("first_name");
                    String surname = rs.getString("last_name");
                    String middleName = rs.getString("middle_name");
                    Date birthday = rs.getDate("birthday");
                    String currentJob = rs.getString("current_job");
                    String street = rs.getString("street");
                    String house = rs.getString("house");
                    String flat = rs.getString("flat");
                    String zipCode = rs.getString("zip_code");
                    String city = rs.getString("city");
                    String country = rs.getString("country");

                    Location location = new Location();
                    location.setCity(city);
                    location.setZipCode(zipCode);
                    location.setCountry(country);
                    Contact contact = new Contact();
                    contact.setId(id);
                    contact.setFirstName(name);
                    contact.setLastName(surname);
                    contact.setMiddleName(middleName);
                    contact.setDateOfBirth(birthday);
                    contact.setCurrentJob(currentJob);
                    contact.setStreet(street);
                    contact.setHouse(house);
                    contact.setFlat(flat);
                    contact.setLocation(location);
                    contacts.add(contact);
                }
            }
        } catch (SQLException e) {
            throw new ContactDAOException("Error occurred while fetching the registered contact types", e);
        } finally {
            ContactDAOUtil.cleanupResources(rs);
        }
        return contacts;
    }

    private Contact getContact(ResultSet rs) throws ContactDAOException {
        Contact contact = new Contact();
        try {
            if (rs.next()) {
                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
                contact.setMiddleName(rs.getString("middle_name"));
                contact.setDateOfBirth(rs.getDate("birthday"));
                contact.setGender(Sex.valueOf(rs.getString("gender")));
                contact.setNationality(rs.getString("nationality"));
                contact.setRelationshipStatus(RelationshipStatus.valueOf(rs.getString("relationship_status")));
                contact.setWebSite(rs.getString("web_site"));
                contact.setEmail(rs.getString("email"));
                contact.setCurrentJob(rs.getString("current_job"));
                Photo photo = new Photo();
                photo.setName(rs.getString("photo_path"));
                contact.setPhoto(photo);
                contact.setStreet(rs.getString("street"));
                contact.setHouse(rs.getString("house"));
                contact.setFlat(rs.getString("flat"));
                contact.setLocationId(rs.getLong("location_id"));
            }
        } catch (SQLException e) {
            throw new ContactDAOException("Error occurred while fetching the registered contact types", e);
        } finally {
            ContactDAOUtil.cleanupResources(rs);
        }
        return contact;
    }

    protected List<Contact> execute(String query, Paging paging, Object... params) throws ContactDAOException {
        Connection connection = ThreadContext.getCurrentConnection();
        List<Contact> contacts = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            contacts = getContacts(rs);
            rs = ps.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                paging.setRecordsSize(rs.getInt(1));
        } catch (SQLException e) {
            throw new ContactDAOException("Error connection", e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contacts;
    }
}


