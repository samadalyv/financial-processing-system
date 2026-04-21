package com.financialprocessingsystem.contact.model;

import com.financialprocessingsystem.common.AbstractEntity;
import com.financialprocessingsystem.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "CONTACTS")
public class Contact extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
