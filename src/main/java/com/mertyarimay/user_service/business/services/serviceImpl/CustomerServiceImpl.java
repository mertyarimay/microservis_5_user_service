package com.mertyarimay.user_service.business.services.serviceImpl;

import com.mertyarimay.user_service.business.dto.CreateCustomerDto;
import com.mertyarimay.user_service.business.services.service.CustomerService;
import com.mertyarimay.user_service.business.services.serviceRules.CustomerServiceRules;
import com.mertyarimay.user_service.data.entity.CustomerEntity;
import com.mertyarimay.user_service.data.entity.UserEntity;
import com.mertyarimay.user_service.data.repository.ICustomerRepository;
import com.mertyarimay.user_service.data.repository.IUserRepository;
import com.mertyarimay.user_service.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
