package com.financialprocessingsystem.contact.controller;

import com.financialprocessingsystem.contact.dto.request.ContactRequest;
import com.financialprocessingsystem.contact.dto.response.ContactResponse;
import com.financialprocessingsystem.contact.service.ContactService;
import com.financialprocessingsystem.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createContact(
            @Valid @RequestBody ContactRequest contact,
            Authentication connectedUser
    ) {
        final long userId = ((User)connectedUser.getPrincipal()).getId();
        contactService.addContact(contact, userId);
    }

    @PutMapping("/{contact-id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateContact(
            @Valid @RequestBody ContactRequest contact,
            @PathVariable("contact-id") Long contactId,
            Authentication connectedUser
    ) {
        final long userId = ((User)connectedUser.getPrincipal()).getId();
        contactService.updateContact(contact, contactId, userId);
    }

    @GetMapping("/{contact-id}")
    public ResponseEntity<ContactResponse> findContactById(
            @PathVariable("contact-id") Long contactId
    ) {
        return ResponseEntity.ok(contactService.findById(contactId));
    }

}
