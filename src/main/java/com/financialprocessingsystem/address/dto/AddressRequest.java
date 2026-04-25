package com.financialprocessingsystem.address.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {

    @NotBlank(message = "Street must not be empty")
    private String street;
    @NotBlank(message = "City must not be empty")
    private String city;
    @NotBlank(message = "State must not be empty")
    private String state;
    @NotBlank(message = "Postal code must not be empty")
    private String postalCode;
    @NotBlank(message = "Country must not be empty")
    private String country;
}
