package com.mertyarimay.user_service.business.services.serviceRules;

import com.mertyarimay.user_service.data.entity.CustomerEntity;
import com.mertyarimay.user_service.data.repository.ICustomerRepository;
import com.mertyarimay.user_service.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceRules {
    private final ICustomerRepository customerRepository;

    public  void checkPhoneNumber(String phoneNumber){
     boolean number =customerRepository.existsByPhoneNumber(phoneNumber);
     if(number==true){
         throw new BusinessException("Aynı telefon Numaralı Kayıt Mevcuttur");
     }
    }


}
