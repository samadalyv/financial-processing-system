package com.financialprocessingsystem.address.mapper;

import com.financialprocessingsystem.address.dto.AddressRequest;
import com.financialprocessingsystem.address.model.Address;
import com.financialprocessingsystem.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddressEntity(AddressRequest addressRequest, User user);
}
