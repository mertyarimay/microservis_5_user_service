package com.mertyarimay.user_service.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginResponse {
    private String id;
    private String email;
    private List<String> roleNames;

}
