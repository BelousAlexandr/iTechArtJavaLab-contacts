package by.belous.contacts.dao.mysql;

public class ContactDAOException extends Exception {

    public ContactDAOException(Throwable cause) {
        super(cause);
    }

    public ContactDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
