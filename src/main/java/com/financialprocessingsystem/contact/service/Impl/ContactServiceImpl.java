package com.financialprocessingsystem.contact.service.Impl;

import com.financialprocessingsystem.contact.dto.request.ContactRequest;
import com.financialprocessingsystem.contact.dto.response.ContactResponse;
import com.financialprocessingsystem.contact.mapper.ContactMapper;
import com.financialprocessingsystem.contact.model.Contact;
import com.financialprocessingsystem.contact.repository.ContactRepository;
import com.financialprocessingsystem.contact.service.ContactService;
import com.financialprocessingsystem.user.model.User;
import com.financialprocessingsystem.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final UserRepository userRepository;


    @Override
    public void addContact(ContactRequest contactRequest, Long userId) {
        // check if the IBAN exists for the user
        contactRepository.existsById(userId);
        if (contactRepository.existsByIbanAndUserId(contactRequest.getIban(), userId)) {
            throw new RuntimeException("Contact already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        Contact contact = contactMapper.toContactEntity(contactRequest);
        contact.setUser(user);
        contactRepository.save(contact);
    }

    @Override
    public void updateContact(ContactRequest contactRequest, Long contactId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        Contact existingContact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact with id " + contactId + " not found"));

        final boolean isNotSameIban = !existingContact.getIban().equals(contactRequest.getIban());
        if (isNotSameIban && contactRepository.existsByIbanAndUserId(contactRequest.getIban(), userId)) {
            throw new RuntimeException("Contact already exists");
        }

        contactMapper.mergeContact(existingContact, contactRequest);
        contactRepository.save(existingContact);
    }

    @Override
    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }

    @Override
    public List<ContactResponse> findAllContacts(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName", "lastName"));

        return contactRepository.findAllByUserId(userId, pageable)
                .getContent()
                .stream()
                .map(contactMapper::toContactResponse)
                .toList();
    }

    @Override
    public ContactResponse findById(Long contactId) {
        return contactRepository.findById(contactId)
                .map(contactMapper::toContactResponse)
                .orElseThrow(() -> new EntityNotFoundException("Contact with id " + contactId + " not found"));
    }

    @Override
    public boolean accountExists(String destinationIban, Long userId) {
        return contactRepository.existsByIbanAndUserId(destinationIban, userId);
    }
}
