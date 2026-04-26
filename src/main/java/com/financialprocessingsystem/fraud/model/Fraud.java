package com.financialprocessingsystem.fraud.model;

import com.financialprocessingsystem.common.AbstractEntity;
import com.financialprocessingsystem.fraud.enums.FraudStatus;
import com.financialprocessingsystem.fraud.enums.FraudType;
import com.financialprocessingsystem.transaction.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "FRAUDS")
public class Fraud extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private FraudType type;
    @Enumerated(EnumType.STRING)
    private FraudStatus status;
    private LocalDateTime date;
    @OneToOne
    private Transaction transaction;
}
