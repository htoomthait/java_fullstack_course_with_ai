package info.htoomaungthait.crash.course.contact.service.impl;

import info.htoomaungthait.crash.course.contact.dto.request.ContactReq;
import info.htoomaungthait.crash.course.contact.dto.response.ContactRes;
import info.htoomaungthait.crash.course.contact.model.Contact;
import info.htoomaungthait.crash.course.contact.respository.ContactRepository;
import info.htoomaungthait.crash.course.contact.service.IContactService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService implements IContactService {

    @Autowired
    private  ContactRepository contactRepository;

    @Override
    public ContactRes addContact(ContactReq contactReq) {

        Contact contact = Contact.of(contactReq);

        Contact savedContact = contactRepository.save(contact);


        return ContactRes.from(savedContact);


    }

    @Override
    public ContactRes updateContact(Long id, ContactReq contactReq) {

        Contact searchedContact = findContactById(id);

        Contact updatedContact = Contact.of(contactReq);
        updatedContact.setId(searchedContact.getId());

        Contact savedContact =  contactRepository.save(updatedContact);

        return ContactRes.from(savedContact);




    }

    @Override
    public ContactRes findById(Long id) {
        return ContactRes.from(findContactById(id));
    }

    @Override
    public boolean deleteContactById(Long id) {


        Contact searchedContact = findContactById(id);

        if(searchedContact != null){
            contactRepository.deleteById(id);

            return true;
        }

        return false;


    }

    @Override
    public List<ContactRes> findAllContacts() {

       List<Contact> contacts = contactRepository.findAll();

       if(contacts.isEmpty()){
           throw new EntityNotFoundException("No contacts found");
       }

       return contacts.stream().map(ContactRes::from).toList();



    }

    private Contact findContactById(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contact not found"));
    }
}
