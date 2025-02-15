package com.mertyarimay.user_service.controller;

import com.mertyarimay.user_service.business.dto.CreateUsersRolesDto;
import com.mertyarimay.user_service.business.services.service.UsersRolesService;
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
@RequestMapping("/users/roles/api")
public class UsersRolesApi {
    private final UsersRolesService usersRolesService;


    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateUsersRolesDto createUsersRolesDto){
        CreateUsersRolesDto createUsersRoles=usersRolesService.create(createUsersRolesDto);
        if(createUsersRoles!=null){
            return ResponseEntity.ok("Kayıt Başarılı");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kayıt BAŞARISIZ.");
        }
    }
}
