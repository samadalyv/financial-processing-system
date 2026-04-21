package com.financialprocessingsystem.account.model;

import com.financialprocessingsystem.common.AbstractEntity;
import com.financialprocessingsystem.user.model.User;
import jakarta.persistence.*;
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
@Table(name = "ACCOUNTS")
public class Account extends AbstractEntity {

    @Column(nullable = false)
    private String iban;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
