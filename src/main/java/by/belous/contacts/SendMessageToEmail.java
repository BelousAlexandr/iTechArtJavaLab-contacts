package by.belous.contacts;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class SendMessageToEmail {

    private Logger logger = LoggerFactory.getLogger(SendMessageToEmail.class);
    private static final String USER = "alexandr.belous.itech@gmail.com";
    private static final String PASSWORD = "DdL2noizemc";

    public void sendMails(by.belous.contacts.entity.Message message) {
        if (textHasContent(message.getMessageBody())) {
            Session session = createSession();
            for (String email : message.getEmails()) {
                if (isValidEmailAddress(email)) {
                    try {
                        sendMessage(session, email, message.getTheme(), message.getMessageBody());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.error("Incorrect email address: " + email);
                }
            }
        } else {
            logger.error("Message can not be empty");
        }
    }

    private Session createSession() {
        java.io.InputStream input = SendMessageToEmail.class.getClassLoader().getResourceAsStream("mail.properties");
        Properties props = new Properties();
        try {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER, PASSWORD);
                    }
                });
    }

    private void sendMessage(Session session, String to, String theme, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USER));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(theme);
        message.setText(text);
        Transport.send(message);
    }

    private boolean isValidEmailAddress(String aEmailAddress){
        if (aEmailAddress == null) return false;

        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(aEmailAddress);
            if (! hasNameAndDomain(aEmailAddress)) {
                result = false;
            }
        }
        catch (AddressException ex){
            result = false;
        }
        return result;
    }

    private boolean hasNameAndDomain(String aEmailAddress){
        String[] tokens = aEmailAddress.split("@");
        return tokens.length == 2 && textHasContent(tokens[0]) && textHasContent(tokens[1]);
    }

    private static boolean textHasContent(String aText){
        return StringUtils.isNotEmpty(aText);
    }
}
