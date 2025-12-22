package info.htoomaungthait.crash.course.contact.controller;

import info.htoomaungthait.crash.course.common.controller.BaseController;
import info.htoomaungthait.crash.course.common.dto.FmtResponse;
import info.htoomaungthait.crash.course.contact.dto.request.ContactReq;
import info.htoomaungthait.crash.course.contact.dto.response.ContactRes;
import info.htoomaungthait.crash.course.contact.model.Contact;
import info.htoomaungthait.crash.course.contact.service.IContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController extends BaseController {

    @Autowired
    private  IContactService iContactService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public FmtResponse<List<ContactRes>> getAllContacts() {

        return buildResponse(
            "CONT_001",
            "OK",
            "Fetched all contacts successfully",
            iContactService.findAllContacts()
        );
    }


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public FmtResponse<ContactRes> getContactById(
            @Validated @PathVariable(required = true) Long id
    ) {
        return buildResponse(
                "CONT_002",
                "OK",
                "Fetched contact by ID successfully",
                iContactService.findById(id)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FmtResponse<ContactRes> addContact(
            @Valid @RequestBody ContactReq contactReq
    ) {

        return buildResponse(
                "CONT_003",
                "OK",
                "contact created successfully",
                iContactService.addContact(contactReq)
        );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public FmtResponse<ContactRes> updateContact(
            @Validated @PathVariable(required = true) Long id,
            @Valid @RequestBody ContactReq contactReq
    ) {
        return buildResponse(
                "CONT_004",
                "OK",
                "contact updated successfully",
                iContactService.updateContact(id, contactReq)
        );
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public FmtResponse<Boolean> deleteContactById(
            @Validated @PathVariable(required = true) Long id
    ) {
        return buildResponse(
                "CONT_005",
                "OK",
                "contact deleted successfully",
                iContactService.deleteContactById(id));


    }


}
