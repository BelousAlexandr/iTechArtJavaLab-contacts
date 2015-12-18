package by.belous.contacts.controller;

import by.belous.contacts.MessageTemplate;
import by.belous.contacts.SendMessageToEmail;
import by.belous.contacts.aspect.TransactionAspect;
import by.belous.contacts.controller.model.ViewModel;
import by.belous.contacts.dao.mysql.ContactDAOException;
import by.belous.contacts.entity.*;
import by.belous.contacts.service.ContactService;
import by.belous.contacts.service.ContactServiceImpl;
import by.belous.contacts.service.FileService;
import by.belous.contacts.service.FileServiceImpl;
import by.belous.contacts.utils.InvocationHandler;
import by.belous.contacts.utils.MessageTemplateUtils;
import by.belous.contacts.validator.ContactValidator;
import by.belous.contacts.validator.Validator;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class ContactController {

    private ContactService contactService;
    private Validator validator;

    private FileService photoFileService;
    private FileService attachmentFileService;
    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    public ContactController() throws IOException {
        this.validator = new ContactValidator();
        contactService = (ContactService) Proxy.newProxyInstance(ContactService.class.getClassLoader(),
                ContactServiceImpl.class.getInterfaces(),
                new InvocationHandler(new ContactServiceImpl(), new TransactionAspect()));

        photoFileService = new FileServiceImpl("D:" + File.separator + "photo");
        attachmentFileService = new FileServiceImpl("D:" + File.separator + "attachments");
    }

    @RequestMapping(path = "/contacts", method = HttpMethod.GET)
    public ViewModel getContactsPage(@QueryParam final Paging paging) throws ContactDAOException {
        ViewModel viewModel = new ViewModel();
        final List<Contact> contacts = contactService.getContacts(paging);
        int pagesCount = (int) Math.ceil(paging.getRecordsSize() * 1.0 / paging.getPageSize());
        paging.setPagesCount(pagesCount);
        logger.info("get contacts: " + String.valueOf(contacts));
        viewModel.setModel(new HashMap<String, Object>() {
            {
                put("contacts", contacts);
                put("paging", paging);
            }
        });
        viewModel.setForward("forward:/home.jsp");
        return viewModel;
    }

    @RequestMapping(path = "/contacts/create", method = HttpMethod.GET)
    public ViewModel getCreateContactPage() {
        ViewModel viewModel = new ViewModel();
        viewModel.setForward("forward:/create-contact.jsp");
        return viewModel;
    }

    @RequestMapping(path = "/contacts/create", method = HttpMethod.POST)
    public ViewModel createContact(@Body final Contact contact, @FileUpload("photoFile") InputStream photo,
                                   @FileUpload("attachment") Map<Long, InputStream> attachments)
            throws IOException, ContactDAOException {
        final List<String> errors = validator.validate(contact);
        ViewModel viewModel = new ViewModel();
        if (CollectionUtils.isNotEmpty(errors)) {
            viewModel.setModel(new HashMap<String, Object>() {
                {
                    put("errors", errors);
                    put("contact", contact);
                }
            });
            viewModel.setForward("forward:/create-contact.jsp");
        } else {
            setPhoto(contact, photo);
            setAttachment(contact, attachments);
            logger.info("saveContact: " + contact);
            contactService.saveContact(contact);
            viewModel.setForward("redirect:/contacts");
        }
        return viewModel;
    }

    @RequestMapping(path = "/contacts/delete", method = HttpMethod.POST)
    public ViewModel deleteContact(@Body Long[] contactId) throws ContactDAOException {
        ViewModel viewModel = new ViewModel();
        logger.debug("deleteContact: " + "contactID: " + Arrays.toString(contactId));
        contactService.deleteContact(contactId);
        viewModel.setForward("redirect:/contacts");
        return viewModel;
    }

    @RequestMapping(path = "/contacts/(\\d+)/edit", method = HttpMethod.POST)
    public ViewModel editContact(@PathParam(0) long contactId, @Body final Contact contact,
                                 @FileUpload("photoFile") InputStream photo,
                                 @FileUpload("attachment") Map<Long, InputStream> attachments)
            throws ContactDAOException, IOException {
        ViewModel viewModel = new ViewModel();
        final List<String> errors = validator.validate(contact);
        if (CollectionUtils.isNotEmpty(errors)) {
            Photo photo1 = contactService.getPhoto(contactId);
            contact.setPhoto(photo1);
            viewModel.setModel(new HashMap<String, Object>() {
                {
                    put("errors", errors);
                    put("contact", contact);
                }
            });
            viewModel.setForward("forward:/create-contact.jsp");
        } else {
            setPhoto(contact, photo);
            setAttachment(contact, attachments);
            logger.info("editContact: " + "contactID: " + contactId + "Contact: " + contact);
            contactService.editContact(contactId, contact);
            viewModel.setForward("redirect:/contacts");
        }
        return viewModel;
    }

    @RequestMapping(path = "/contacts/(\\d+)/edit", method = HttpMethod.GET)
    public ViewModel getEditPage(@PathParam(0) Long contactId) throws ContactDAOException {
        ViewModel viewModel = new ViewModel();

        final Contact contact = contactService.getContact(contactId);
        logger.info("get contactById: contactID: " + contactId + " contact: " + contact);
        viewModel.setModel(new TreeMap<String, Object>() {
            {
                put("contact", contact);
            }
        });
        viewModel.setForward("forward:/create-contact.jsp");
        return viewModel;
    }

    @RequestMapping(path = "/contacts/compose", method = HttpMethod.POST)
    public ViewModel getComposeEmailPage(@Body final Long[] contactId) throws ContactDAOException {
        final MessageTemplate template = new MessageTemplate();
        ViewModel viewModel = new ViewModel();
        final List<Contact> contacts = contactService.composeContacts(contactId);
        logger.info("get contactsById: " + contacts + " contactId: " + Arrays.toString(contactId));
        MessageTemplateUtils.buildTemplates(contacts, template, "stringTemplate/birthday.stg");
        viewModel.setModel(new HashMap<String, Object>() {
            {
                put("template", template);
            }
        });
        viewModel.setForward("forward:/send-email.jsp");
        return viewModel;
    }

    @RequestMapping(path = "/contacts/send", method = HttpMethod.POST)
    public ViewModel sendMail(@Body final Message message) throws ContactDAOException {
        SendMessageToEmail admin = new SendMessageToEmail();
        admin.sendMails(message);
        ViewModel viewModel = new ViewModel();
        viewModel.setForward("redirect:/contacts");
        return viewModel;
    }

    @RequestMapping(path = "/contacts/search/", method = HttpMethod.GET)
    public ViewModel getSearchContactsPage() throws ContactDAOException {
        ViewModel model = new ViewModel();
        model.setForward("forward:/search-contacts.jsp");
        return model;
    }

    @RequestMapping(path = "/contacts/search?(\\w+)", method = HttpMethod.GET)
    public ViewModel searchContacts(@QueryParam final ContactFilter filter, @QueryParam Paging paging) throws ContactDAOException {
        final ViewModel model = new ViewModel();
        final List<Contact> contacts = contactService.searchContacts(filter);
        logger.info("get contacts search: " + contacts + " filter: " + filter);
        model.setModel(new HashMap<String, Object>() {
            {
                put("contacts", contacts);
            }
        });
        model.setForward("forward:/home.jsp");
        return model;
    }

    @RequestMapping(path = "/contacts/(\\d+)/profile-img", method = HttpMethod.GET)
    public void uploadImage(@PathParam(0) final Long contactId, HttpServletResponse response) throws ContactDAOException, IOException {
        final Contact contact = contactService.getContact(contactId);
        String fileName = contact.getPhoto().getName();
        buildHeaderAttachment(response, photoFileService, fileName, fileName);

    }

    @RequestMapping(path = "/contacts/(\\d+)/attachment/(\\d+)", method = HttpMethod.GET)
    public void uploadAttachment(@PathParam(0) final Long contactId, @PathParam(1) final long attachmentId,
                                 HttpServletResponse response) throws ContactDAOException, IOException {
        final Contact contact = contactService.getContact(contactId);
        List<Attachment> attachments = contact.getAttachments();
        for (Attachment attachment : attachments) {
            if (attachmentId == attachment.getAttachmentId()) {
                String fileName = attachment.getFileName();
                String fullPath = attachment.getFullPath();
                buildHeaderAttachment(response, attachmentFileService, fileName, fullPath);
            }
        }
    }

    private void buildHeaderAttachment(HttpServletResponse response, FileService fileService, String fileName,
                                       String fullPath) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = fileService.readFile(fullPath);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            response.addHeader("Content-Type", "application/octet-stream");
            ByteStreams.copy(inputStream, response.getOutputStream());
        } finally {
            Closeables.closeQuietly(inputStream);
        }
    }

    private void setPhoto(@Body Contact contact, @FileUpload("photoFile") InputStream photo) throws IOException {
        if (photo != null) {
            logger.debug("writeFile: args:" + photo);
            if (photo.available() > 0) {
                String photoFileFullPath = photoFileService.writeFile(photo);
                Photo photoPath = new Photo();
                photoPath.setName(photoFileFullPath);
                contact.setPhoto(photoPath);
            }
        }
    }

    private void setAttachment(@Body Contact contact, @FileUpload("attachment") Map<Long, InputStream> attachments)
            throws IOException {
        if (attachments != null) {
            for (Map.Entry<Long, InputStream> integerInputStreamEntry : attachments.entrySet()) {
                Long index = integerInputStreamEntry.getKey();
                InputStream inputStream = integerInputStreamEntry.getValue();
                String fileFullPath = attachmentFileService.writeFile(inputStream);
                logger.debug("file path:" + fileFullPath);
                List<Attachment> listAttach = contact.getAttachments();
                for (Attachment attachment : listAttach) {
                    if (Objects.equals(attachment.getAttachmentId(), index)) {
                        attachment.setFullPath(fileFullPath);
                    }
                }
            }
        }
    }


}
