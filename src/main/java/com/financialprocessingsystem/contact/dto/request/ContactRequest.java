package com.financialprocessingsystem.contact.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactRequest {

    @NotBlank(message = "first Name must not be empty")
    private String firstName;
    @NotBlank(message = "last Name must not be empty")
    private String lastName;
    @NotBlank(message = "iban must not be empty")
    private String iban;
}
