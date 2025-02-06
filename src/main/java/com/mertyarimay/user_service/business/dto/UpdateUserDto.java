package com.mertyarimay.user_service.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDto {
    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min = 6, message = "Şifre en az 6 karakter uzunluğunda olmalıdır.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[.])[A-Za-z\\d.]{6,}$",
            message = "Şifre en az bir büyük harf, bir küçük harf, bir rakam ve bir nokta (.) içermelidir."
    )
    private String password;



    private String oldPassword;

}
