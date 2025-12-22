package info.htoomaungthait.crash.course.contact.service;

import info.htoomaungthait.crash.course.contact.dto.request.ContactReq;
import info.htoomaungthait.crash.course.contact.dto.response.ContactRes;
import info.htoomaungthait.crash.course.contact.model.Contact;

import java.util.List;

public interface IContactService {

    ContactRes addContact(ContactReq contact);

    ContactRes updateContact(Long id, ContactReq contact);

    ContactRes findById(Long id);

    boolean deleteContactById(Long id);

    List<ContactRes> findAllContacts();
}
