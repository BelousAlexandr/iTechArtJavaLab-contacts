package by.belous.contacts.dao.mysql;

import by.belous.contacts.entity.Attachment;

import java.util.List;

public interface AttachmentDAO {
    List<Attachment> getAttachments(Long id) throws ContactDAOException;

    void save(Attachment attachment) throws ContactDAOException;

    void edit(Attachment attachment) throws ContactDAOException;

    void deleteById(Long attachmentId) throws ContactDAOException;

    void deleteByContactId(Long contactId) throws ContactDAOException;
}
