package com.mertyarimay.user_service.business.services.service;

import com.mertyarimay.user_service.business.dto.CreateUserDto;
import com.mertyarimay.user_service.business.dto.UpdateUserDto;

public interface UserService {

    CreateUserDto create(CreateUserDto createUserDto);
    CreateUserDto login(CreateUserDto createUserDto);
    UpdateUserDto updateUser(UpdateUserDto updateUserDto, int id);
    boolean delete (int id);
}
