package com.mertyarimay.user_service.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCustomerDto {
    @NotNull
    @NotBlank
    @Size(min = 10,max = 10)
    private String phoneNumber;
}
