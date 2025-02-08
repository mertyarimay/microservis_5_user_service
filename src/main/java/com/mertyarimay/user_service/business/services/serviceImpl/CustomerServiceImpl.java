package com.mertyarimay.user_service.business.services.serviceImpl;

import com.mertyarimay.user_service.business.dto.CreateCustomerDto;
import com.mertyarimay.user_service.business.dto.GetAllCustomerDto;
import com.mertyarimay.user_service.business.dto.GetByIdCustomerDto;
import com.mertyarimay.user_service.business.dto.UpdateCustomerDto;
import com.mertyarimay.user_service.business.services.service.CustomerService;
import com.mertyarimay.user_service.business.services.serviceRules.CustomerServiceRules;
import com.mertyarimay.user_service.data.entity.CustomerEntity;
import com.mertyarimay.user_service.data.entity.UserEntity;
import com.mertyarimay.user_service.data.repository.ICustomerRepository;
import com.mertyarimay.user_service.data.repository.IUserRepository;
import com.mertyarimay.user_service.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final IUserRepository userRepository;
    private final ICustomerRepository customerRepository;
    private final ModelMapperService modelMapperService;
    private final CustomerServiceRules customerServiceRules;
    @Override
    public CreateCustomerDto create(CreateCustomerDto createCustomerDto) {
        UserEntity userEntity=userRepository.findById(createCustomerDto.getUserId()).orElse(null);
        if(userEntity!=null){
            createCustomerDto.setEmail(userEntity.getEmail());
            CustomerEntity customerEntity=modelMapperService.forRequest().map(createCustomerDto,CustomerEntity.class);
            customerEntity.setUserEntity(userEntity);
            customerServiceRules.checkPhoneNumber(customerEntity.getPhoneNumber());
            customerRepository.save(customerEntity);
            CreateCustomerDto createCustomer=modelMapperService.forRequest().map(customerEntity, CreateCustomerDto.class);
            return createCustomer;
        }
        return null;

    }
    @Override
    public List<GetAllCustomerDto> getAll() {
        List<CustomerEntity>customerEntities=customerRepository.findAll();
        List<GetAllCustomerDto>customerDtos=customerEntities.stream()
                .map(customerEntity -> modelMapperService.forResponse()
                        .map(customerEntity,GetAllCustomerDto.class)).collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public GetByIdCustomerDto getById(int id) {
        CustomerEntity customerEntity=customerRepository.findById(id).orElse(null);
       if(customerEntity!=null){
           GetByIdCustomerDto customerDto=modelMapperService.forResponse().map(customerEntity,GetByIdCustomerDto.class);
           return customerDto;
       }
       return null;
    }

    @Override
    public UpdateCustomerDto update(UpdateCustomerDto updateCustomerDto,int id) {
        CustomerEntity customerEntity=customerRepository.findById(id).orElse(null);
        if(customerEntity!=null){
            customerServiceRules.checkPhoneNumber(updateCustomerDto.getPhoneNumber());
            customerEntity.setPhoneNumber(updateCustomerDto.getPhoneNumber());
            customerRepository.save(customerEntity);
            UpdateCustomerDto updateCustomer=modelMapperService.forRequest().map(customerEntity,UpdateCustomerDto.class);
            return updateCustomer;
        }
        else {
            return null;
        }

    }
}
