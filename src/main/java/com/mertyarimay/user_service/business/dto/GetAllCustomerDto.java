package com.mertyarimay.user_service.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllCustomerDto {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
}
