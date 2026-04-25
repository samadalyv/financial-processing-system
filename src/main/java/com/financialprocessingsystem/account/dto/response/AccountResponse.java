package com.financialprocessingsystem.account.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private Long id;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String accountIban;
    private LocalDateTime createdAt;
    private boolean locked;
}
