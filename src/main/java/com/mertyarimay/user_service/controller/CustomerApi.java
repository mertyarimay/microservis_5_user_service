package com.mertyarimay.user_service.controller;

import com.mertyarimay.user_service.business.dto.CreateCustomerDto;
import com.mertyarimay.user_service.business.services.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/customer/api")
public class CustomerApi {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateCustomerDto createCustomerDto){
        CreateCustomerDto createCustomer=customerService.create(createCustomerDto);
        if(createCustomer!=null){
            return ResponseEntity.ok("Müşteri kaydınız oluşturulmuştur iyi alışverişler");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bir Kullanıcı Kaydınız Bulunmamaktadır");
        }
    }
}
