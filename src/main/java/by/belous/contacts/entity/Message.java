package by.belous.contacts.entity;

import java.util.List;

public class Message {
    private List<String> emails;
    private String theme;


    public Message(){}
    public Message(List<String> emails, String theme, String messageBody) {
        this.emails = emails;
        this.theme = theme;
        this.messageBody = messageBody;
    }

    private String messageBody;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "Message{" +
                "emails = " + emails +
                ", theme = " + theme +
                ", messageBody = " + messageBody + '}';
    }
}
