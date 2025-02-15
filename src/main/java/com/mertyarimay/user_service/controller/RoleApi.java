package com.mertyarimay.user_service.controller;

import com.mertyarimay.user_service.business.dto.CreateRoleDto;
import com.mertyarimay.user_service.business.services.service.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/role/api")
public class RoleApi {
    private final RoleService roleService;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateRoleDto createRoleDto){
        CreateRoleDto createRole=roleService.create(createRoleDto);
        if(createRole!=null){
            return ResponseEntity.ok("Kayıt İşleminiz Başarılı bir şekilde oluştu");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kayıt İşlemi Başarısız");
        }
    }
}
