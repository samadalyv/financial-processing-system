package com.financialprocessingsystem.account.dto.request;

import com.financialprocessingsystem.address.dto.AddressRequest;
import com.financialprocessingsystem.user.dto.request.UserRequest;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @Valid
    private UserRequest user;

    @Valid
    private AddressRequest address;
}
