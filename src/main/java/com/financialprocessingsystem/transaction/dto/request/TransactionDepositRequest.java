package com.financialprocessingsystem.transaction.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDepositRequest {

    @NotNull(message = "Amount must not be null")
    @Positive(message = "The deposit amount must be a positive number.")
    private BigDecimal depositAmount;
}
