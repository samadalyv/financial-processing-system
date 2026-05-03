package com.financialprocessingsystem.transaction.model;

import com.financialprocessingsystem.common.AbstractEntity;
import com.financialprocessingsystem.fraud.model.Fraud;
import com.financialprocessingsystem.transaction.model.enums.TransactionStatus;
import com.financialprocessingsystem.transaction.model.enums.TransactionType;
import com.financialprocessingsystem.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "TRANSACTIONS")
public class Transaction extends AbstractEntity {


    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String destinationIban;
    private String sourceIban;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    private Fraud fraud;


}
