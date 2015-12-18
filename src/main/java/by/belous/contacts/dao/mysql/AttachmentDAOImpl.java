package by.belous.contacts.dao.mysql;

import by.belous.contacts.dao.mysql.util.ContactDAOUtil;
import by.belous.contacts.entity.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttachmentDAOImpl extends AbstractDAO implements AttachmentDAO {
    private Logger logger = LoggerFactory.getLogger(AttachmentDAOImpl.class);

    @Override
    public List<Attachment> getAttachments(Long id) throws ContactDAOException {
        String query = "select a.attachment_id, a.date_upload, a.file_name, a.file_path, a.attachment_comment " +
                "from attachment as a where a.isDeleted=0 AND a.contact_id=?";
        logger.debug(query + "contact_id= " + id);
        ResultSet rs = executeQuery(query, id);
        List<Attachment> attachments = getAttachments(rs);
        logger.debug("attachments: " + attachments);
        return attachments;
    }

    @Override
    public void save(Attachment attachment) throws ContactDAOException {
        String query = "insert into attachment (attachment_comment, file_name, contact_id, file_path) " +
                "VALUES(?, ?, ?, ?)";
        executeUpdate(query, attachment.getComment(), attachment.getFileName(), attachment.getContactId(),
                attachment.getFullPath());
    }

    @Override
    public void edit(Attachment attachment) throws ContactDAOException {
        String query = "update attachment set attachment_comment=?, file_name=?, isDeleted=? where " +
                "attachment_id=? AND contact_id=?";
        logger.debug(query + "attachment: " + attachment);
        executeUpdate(query, attachment.getComment(), attachment.getFileName(), attachment.getDeleted(),
                attachment.getAttachmentId(), attachment.getContactId());
    }

    @Override
    public void deleteById(Long attachmentId) throws ContactDAOException {
        String query = "update attachment set isDeleted=1 where attachment_id=?";
        logger.debug(query + "contactID: " + attachmentId);
        executeUpdate(query, attachmentId);
    }

    @Override
    public void deleteByContactId(Long contactId) throws ContactDAOException {
        String query = "update attachment set isDeleted=1 where contact_id=?";
        logger.debug(query + "contactID: " + contactId);
        executeUpdate(query, contactId);
    }

    private List<Attachment> getAttachments(ResultSet rs) throws ContactDAOException {
        List<Attachment> attachments = new ArrayList<>();
        try {
            while (rs.next()) {
                Long attachmentId = rs.getLong("attachment_id");
                String fullPath = rs.getString("file_path");
                String fileName = rs.getString("file_name");
                String comment = rs.getString("attachment_comment");
                Date dateOfDownload = rs.getDate("date_upload");
                Attachment attachment = new Attachment();
                attachment.setAttachmentId(attachmentId);
                attachment.setFullPath(fullPath);
                attachment.setFileName(fileName);
                attachment.setDateOfDownload(dateOfDownload);
                attachment.setComment(comment);
                attachment.setDeleted(false);
                attachment.setEdited(false);
                attachments.add(attachment);
            }
        } catch (SQLException e) {
            throw new ContactDAOException("Error occurred while fetching the registered attachment types", e);
        } finally {
            ContactDAOUtil.cleanupResources(rs);
        }
        return attachments;
    }
}
