package by.belous.contacts.dao.mysql.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAOUtil {

    private static Logger LOG = LoggerFactory.getLogger(ContactDAOUtil.class);
    public static void cleanupResources(ResultSet rs) {
        if (rs != null) {
            try {
                LOG.debug("close ResultSet");
                rs.close();
            } catch (SQLException e) {
                LOG.error("Error occurred while closing result set", e);
            }
        }
    }


}
