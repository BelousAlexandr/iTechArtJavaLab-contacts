package by.belous.contacts.service;

import by.belous.contacts.MessageTemplate;
import by.belous.contacts.SendMessageToEmail;
import by.belous.contacts.aspect.TransactionAspect;
import by.belous.contacts.dao.mysql.ContactDAOException;
import by.belous.contacts.entity.Contact;
import by.belous.contacts.entity.Message;
import by.belous.contacts.utils.InvocationHandler;
import by.belous.contacts.utils.MessageTemplateUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.List;

public class Mailer {

    final private MessageTemplate template;
    final private ContactService contactService;
    private Logger logger = LoggerFactory.getLogger(Mailer.class);

    public Mailer() {
        this.template = new MessageTemplate();
        this.contactService = (ContactService) Proxy.newProxyInstance(ContactService.class.getClassLoader(),
                ContactServiceImpl.class.getInterfaces(), new InvocationHandler(new ContactServiceImpl(),
                        new TransactionAspect()));
    }

    public void sendEmail(Calendar date) throws ContactDAOException {
        List<Contact> contactsByBirthday = contactService.getContactsByBirthday(date);
        logger.debug("end getContactsByBirthday, return contactsByBirthday: " + contactsByBirthday);
        MessageTemplateUtils.buildTemplates(contactsByBirthday, template, "stringTemplate/birthday.stg");
        List<String> emails = Lists.transform(contactsByBirthday, toEmails());
        String messageBody = template.getTemplate().get("happyBirthdayAll");
        SendMessageToEmail admin = new SendMessageToEmail();
        String theme = "День рождения";
        logger.debug("sendMail, emails: " + emails + " theme: " + theme );
        admin.sendMails(new Message(emails, theme, messageBody));
    }

    private Function<Contact, String> toEmails() {
        return new Function<Contact, String>() {
            @Override
            public String apply(Contact input) {
                return input.getEmail();
            }
        };
    }


}
