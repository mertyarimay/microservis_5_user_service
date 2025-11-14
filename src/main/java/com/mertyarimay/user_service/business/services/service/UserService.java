package com.mertyarimay.user_service.business.services.service;

import com.mertyarimay.user_service.business.dto.CreateUserDto;
import com.mertyarimay.user_service.business.dto.UpdateUserDto;
import com.mertyarimay.user_service.business.dto.UserLoginResponse;

public interface UserService {

    CreateUserDto create(CreateUserDto createUserDto);
    UserLoginResponse login(CreateUserDto createUserDto);
    UpdateUserDto updateUser(UpdateUserDto updateUserDto, int id,String token);
    boolean delete (int id,String token);
}
