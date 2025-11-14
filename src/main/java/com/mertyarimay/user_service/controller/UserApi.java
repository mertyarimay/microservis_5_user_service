package com.mertyarimay.user_service.controller;

import com.mertyarimay.user_service.business.dto.CreateUserDto;
import com.mertyarimay.user_service.business.dto.UpdateUserDto;
import com.mertyarimay.user_service.business.dto.UserLoginResponse;
import com.mertyarimay.user_service.business.services.service.UserService;
import com.mertyarimay.user_service.exception.BusinessException;
import com.mertyarimay.user_service.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user/api")
public class UserApi {
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateUserDto createUserDto){
        CreateUserDto createUser=userService.create(createUserDto);
        if(createUser!=null){
            return ResponseEntity.ok("Kayıt İşlemimiz Başarılı Bir Şekilde Oluştu");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kayıt İşlemi Başarısız");
        }
    }
    @PostMapping("/login")
    public String login(@RequestBody @Valid CreateUserDto createUserDto){
        UserLoginResponse userLoginResponse=userService.login(createUserDto);
        if(userLoginResponse!=null){
            return jwtUtil.generateToken(userLoginResponse.getEmail(),userLoginResponse.getId(),userLoginResponse.getRoleNames());
        }else {
            throw new BusinessException("Geçersiz mail veya şifre girdiniz");

        }
}
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateUserDto updateUserDto, @PathVariable("id") int id,@RequestHeader("Authorization")String token){
        UpdateUserDto updateUser=userService.updateUser(updateUserDto,id,token);
        if(updateUser!=null){
            return ResponseEntity.ok("Güncelleme işleminiz başarılı olmuştur");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Güncelleme İşlemi Başarısız olmuştur");
        }
    }
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id,@RequestHeader("Authorization")String token){
        boolean delete=userService.delete(id,token);
        if(delete==true){
            return ResponseEntity.ok("Silme işlemi Başarılı olmuştur");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt Silme İşlemi Başarısız");
        }

    }

}
