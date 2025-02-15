package com.mertyarimay.user_service.controller;

import com.mertyarimay.user_service.business.dto.CreateCustomerDto;
import com.mertyarimay.user_service.business.dto.GetAllCustomerDto;
import com.mertyarimay.user_service.business.dto.GetByIdCustomerDto;
import com.mertyarimay.user_service.business.dto.UpdateCustomerDto;
import com.mertyarimay.user_service.business.services.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<GetAllCustomerDto>getAll(){
        List<GetAllCustomerDto>getAllCustomerDtos=customerService.getAll();
        return getAllCustomerDtos;
    }
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable ("id") int id){
        GetByIdCustomerDto customerDto=customerService.getById(id);
        if(customerDto!=null){
            return ResponseEntity.ok(customerDto);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id Bulunamadı");
        }

    }
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateCustomerDto updateCustomerDto,@PathVariable("id") int id){
        UpdateCustomerDto updateCustomer=customerService.update(updateCustomerDto,id);
        if(updateCustomer!=null){
            return ResponseEntity.ok("Güncelleme İşleminiz Başarılı Bir Şekilde Gerçekleşti");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Güncellemek istediğiniz ID Bulunamadı");
        }
    }
}
