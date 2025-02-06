package com.mertyarimay.user_service.controller;

import com.mertyarimay.user_service.business.dto.CreateUserDto;
import com.mertyarimay.user_service.business.dto.UpdateUserDto;
import com.mertyarimay.user_service.business.services.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user/api")
public class UserApi {
    private final UserService userService;


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
    public ResponseEntity<Object>login(@RequestBody @Valid CreateUserDto createUserDto){
        CreateUserDto createUser=userService.login(createUserDto);
        if(createUser!=null){
            return ResponseEntity.ok("Login İşlemi Başarılı");
        }else {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email veya Şifren Yanlış");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateUserDto updateUserDto, @PathVariable("id") int id){
        UpdateUserDto updateUser=userService.updateUser(updateUserDto,id);
        if(updateUser!=null){
            return ResponseEntity.ok("Güncelleme işleminiz başarılı olmuştur");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Güncelleme İşlemi Başarısız olmuştur");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id){
        boolean delete=userService.delete(id);
        if(delete==true){
            return ResponseEntity.ok("Silme işlemi Başarılı olmuştur");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt Silme İşlemi Başarısız");
        }

    }

}
