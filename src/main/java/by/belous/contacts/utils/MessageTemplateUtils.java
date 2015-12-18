package by.belous.contacts.utils;

import by.belous.contacts.MessageTemplate;
import by.belous.contacts.entity.Contact;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.List;
import java.util.Map;

public class MessageTemplateUtils {

    public static void buildTemplates(List<Contact> contacts, MessageTemplate template, String filePath) {
        STGroup group = new STGroupFile(filePath);
        StringBuilder sb = new StringBuilder();
        setTemplateParameters(contacts, template, sb);
        addTemplate("happyBirthdayAll", "happyBirthdayAll", template, group, sb);
        addTemplate("happyBirthdayRU", "Happy Birthday in russian", template, group, sb);
        addTemplate("happyBirthdayEN", "Happy Birthday in english", template, group, sb);
    }

    private static void addTemplate(String instanceName, String templateName, MessageTemplate template,
                                    STGroup group, StringBuilder sb) {
        ST st = group.getInstanceOf(instanceName);
        Map<String, Object> attributes = st.getAttributes();
        for (Map.Entry<String, Object> stringObjectEntry : attributes.entrySet()) {
            String key = stringObjectEntry.getKey();
            if ("text".equals(key)) {
                st.add(key, " ");
            } else {
                st.add(key, sb.toString());
            }
            template.getTemplate().put(templateName, st.render());
        }
    }

    private static void setTemplateParameters(List<Contact> contacts, MessageTemplate template, StringBuilder sb) {
        for (Contact contact : contacts) {
            sb.append(contact.getLastName()).append(" ").append(contact.getFirstName()).append(" ").
                    append(contact.getMiddleName()).append(" ");
            template.getContactsNames().add(sb.toString());
            template.getEmails().add(contact.getEmail());
        }
    }

}
