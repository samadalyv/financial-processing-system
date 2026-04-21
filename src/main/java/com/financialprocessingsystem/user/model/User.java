package com.financialprocessingsystem.user.model;


import com.financialprocessingsystem.account.model.Account;
import com.financialprocessingsystem.address.model.Address;
import com.financialprocessingsystem.common.AbstractEntity;
import com.financialprocessingsystem.contact.model.Contact;
import com.financialprocessingsystem.role.model.Role;
import com.financialprocessingsystem.transaction.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "USERS")
public class User extends AbstractEntity {

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    @OneToOne(mappedBy = "user")
    private Account account;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
    private boolean isActive;

    @ManyToOne
    private Role role;


    @OneToOne(mappedBy = "user")
    private Address address;

    public String fullName(){
        return firstName + " " + lastName;
    }

}
