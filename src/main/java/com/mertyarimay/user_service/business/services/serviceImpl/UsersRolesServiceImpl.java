package com.mertyarimay.user_service.business.services.serviceImpl;

import com.mertyarimay.user_service.business.dto.CreateUsersRolesDto;
import com.mertyarimay.user_service.business.services.service.UsersRolesService;
import com.mertyarimay.user_service.data.entity.users_roles;
import com.mertyarimay.user_service.data.repository.IUsersRolesRepository;
import com.mertyarimay.user_service.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersRolesServiceImpl implements UsersRolesService {
    private  final IUsersRolesRepository usersRolesRepository;
    private final ModelMapperService modelMapperService;
    @Override
    public CreateUsersRolesDto create(CreateUsersRolesDto createUsersRolesDto) {
        users_roles userRoles=modelMapperService.forRequest().map(createUsersRolesDto, users_roles.class);
        usersRolesRepository.save(userRoles);
        CreateUsersRolesDto createUsersRoles=modelMapperService.forRequest().map(userRoles,CreateUsersRolesDto.class);
        return createUsersRoles;
    }
}
