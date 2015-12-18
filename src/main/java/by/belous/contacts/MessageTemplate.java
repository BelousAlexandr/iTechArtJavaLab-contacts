package by.belous.contacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MessageTemplate {
    private List<String> emails;
    private List<String> contactsNames;
    private Map<String, String> template;

    public MessageTemplate() {
        emails = new ArrayList<>();
        contactsNames = new ArrayList<>();
        template = new LinkedHashMap<>();
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getContactsNames() {
        return contactsNames;
    }

    public void setContactsNames(List<String> contactsNames) {
        this.contactsNames = contactsNames;
    }

    public Map<String, String> getTemplate() {
        return template;
    }

    public void setTemplate(Map<String, String> template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "MessageTemplate{" +
                "emails = " + emails +
                ", contacts = " + contactsNames +
                ", template = " + template +
                '}';
    }
}
