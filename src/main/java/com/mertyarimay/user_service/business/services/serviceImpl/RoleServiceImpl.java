package com.mertyarimay.user_service.business.services.serviceImpl;

import com.mertyarimay.user_service.business.dto.CreateRoleDto;
import com.mertyarimay.user_service.business.services.service.RoleService;
import com.mertyarimay.user_service.data.entity.RoleEntity;
import com.mertyarimay.user_service.data.repository.IRoleRepository;
import com.mertyarimay.user_service.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final ModelMapperService modelMapperService;
    private final IRoleRepository roleRepository;

    @Override
    public CreateRoleDto create(CreateRoleDto createRoleDto) {
        RoleEntity roleEntity=modelMapperService.forRequest().map(createRoleDto,RoleEntity.class);
        roleRepository.save(roleEntity);
        CreateRoleDto createRole=modelMapperService.forRequest().map(roleEntity,CreateRoleDto.class);
        return  createRole;
    }
}
