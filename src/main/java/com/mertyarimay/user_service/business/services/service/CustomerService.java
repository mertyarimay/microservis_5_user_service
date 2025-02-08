package com.mertyarimay.user_service.business.services.service;

import com.mertyarimay.user_service.business.dto.CreateCustomerDto;
import com.mertyarimay.user_service.business.dto.GetAllCustomerDto;
import com.mertyarimay.user_service.business.dto.GetByIdCustomerDto;
import com.mertyarimay.user_service.business.dto.UpdateCustomerDto;

import java.util.List;


public interface CustomerService {
    CreateCustomerDto create(CreateCustomerDto createCustomerDto);
    UpdateCustomerDto update(UpdateCustomerDto updateCustomerDto ,int id);
    List<GetAllCustomerDto>getAll();
    GetByIdCustomerDto getById(int id);
}
