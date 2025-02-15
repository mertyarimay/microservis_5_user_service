package com.mertyarimay.user_service.business.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsersRolesDto {
    @NotNull
    private int userId;
    @NotNull
    private int roleId;

}
