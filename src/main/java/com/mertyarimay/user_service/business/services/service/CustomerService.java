package com.mertyarimay.user_service.business.services.service;

import com.mertyarimay.user_service.business.dto.CreateCustomerDto;

public interface CustomerService {
    CreateCustomerDto create(CreateCustomerDto createCustomerDto);
}
