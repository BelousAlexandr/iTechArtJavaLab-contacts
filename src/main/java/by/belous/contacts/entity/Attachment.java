package by.belous.contacts.entity;

import java.util.Date;

public class Attachment {

    private Long attachmentId;
    private String fullPath;
    private String fileName;
    private Date dateOfDownload;
    private String comment;
    private Boolean isDeleted;
    private Boolean isEdited;
    private Long contactId;

    public Boolean getHasInDB() {
        return hasInDB;
    }

    public void setHasInDB(Boolean hasInDB) {
        this.hasInDB = hasInDB;
    }

    private Boolean hasInDB;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Boolean getEdited() {
        return isEdited;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setEdited(Boolean edited) {
        isEdited = edited;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getDateOfDownload() {
        return dateOfDownload;
    }

    public void setDateOfDownload(Date dateOfDownload) {
        this.dateOfDownload = dateOfDownload;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "isDeleted = " + isDeleted +
                ", contactId = " + contactId +
                ", comment = " + comment +
                ", attachmentId = " + attachmentId +
                ", fullPath = " + fullPath +
                ", fileName = " + fileName +
                ", dateOfDownload = " + dateOfDownload +
                '}';
    }
}
