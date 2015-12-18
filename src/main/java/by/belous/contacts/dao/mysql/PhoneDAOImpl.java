package by.belous.contacts.dao.mysql;

import by.belous.contacts.dao.mysql.util.ContactDAOUtil;
import by.belous.contacts.entity.Phone;
import by.belous.contacts.entity.PhoneType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDAOImpl extends AbstractDAO implements PhoneDAO {

    private Logger logger = LoggerFactory.getLogger(PhoneDAOImpl.class);

    @Override
    public void save(Phone phone) throws ContactDAOException {
        String query = "insert into phone (country_code, operator_code, phone_number, phone_type, " +
                "contact_id, phone_comment) VALUES(?, ?, ?, ?, ?, ?)";
        logger.debug(query + " phone: " + phone);
        executeUpdate(query, phone.getCountryCode(), phone.getOperatorCode(),
                phone.getPhoneNumber(), phone.getPhoneType().name(), phone.getContactId(), phone.getComment());
    }

    @Override
    public List<Phone> getPhonesByContactId(Long id) throws ContactDAOException {
        String query = "select p.phone_id, p.country_code, p.operator_code, p.phone_number, p.phone_type, " +
                "p.phone_comment from phone as p where p.isDeleted=0 AND p.contact_id=?";
        logger.debug(query + " phone_id: " + id);
        ResultSet rs = executeQuery(query, id);
        return getPhones(rs);
    }

    @Override
    public void edit(Phone phone) throws ContactDAOException {
        String query = "update phone set country_code=?, operator_code=?, phone_number=?, phone_type=?, " +
                "phone_comment=?, isDeleted=? where phone_id=? AND contact_id=?";
        logger.debug(query + " phone: " + phone);
        executeUpdate(query, phone.getCountryCode(), phone.getOperatorCode(),
                phone.getPhoneNumber(), phone.getPhoneType().name(), phone.getComment(),
                phone.getDeleted(), phone.getPhoneId(), phone.getContactId());
    }

    @Override
    public void deleteById(Long phoneId) throws ContactDAOException {
        String query = "update phone set isDeleted=1 where phone_id=?";
        logger.debug(query + " phoneID: " + phoneId);
        executeUpdate(query, phoneId);

    }

    @Override
    public void deleteByContactId(Long id) throws ContactDAOException {
        String query = "update phone set isDeleted=1 where contact_id=?";
        logger.debug(query + " contactID: " + id);
        executeUpdate(query, id);
    }

    private List<Phone> getPhones(ResultSet rs) throws ContactDAOException {
        List<Phone> phones = new ArrayList<>();
        try {
            while (rs.next()) {
                Long phoneId = rs.getLong("phone_id");
                Short countryCode = rs.getShort("country_code");
                Short operatorCode = rs.getShort("operator_code");
                Long phoneNumber = rs.getLong("phone_number");
                PhoneType phoneType = PhoneType.valueOf(rs.getString("phone_type"));
                String comment = rs.getString("phone_comment");
                Phone phone = new Phone();
                phone.setPhoneId(phoneId);
                phone.setCountryCode(countryCode);
                phone.setOperatorCode(operatorCode);
                phone.setPhoneNumber(phoneNumber);
                phone.setPhoneType(phoneType);
                phone.setComment(comment);
                phones.add(phone);
            }
        } catch (SQLException e) {
            throw new ContactDAOException("Error occurred while fetching the registered phone types", e);
        } finally {
            ContactDAOUtil.cleanupResources(rs);
        }
        return phones;
    }
}
