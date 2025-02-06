package com.mertyarimay.user_service.business.services.serviceImpl;

import com.mertyarimay.user_service.business.dto.CreateUserDto;
import com.mertyarimay.user_service.business.dto.UpdateUserDto;
import com.mertyarimay.user_service.business.services.service.UserService;
import com.mertyarimay.user_service.data.entity.UserEntity;
import com.mertyarimay.user_service.data.repository.ICustomerRepository;
import com.mertyarimay.user_service.data.repository.IUserRepository;
import com.mertyarimay.user_service.mappers.ModelMapperService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final IUserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;
    private final ICustomerRepository customerRepository;


    @Override
    @Transactional
    public CreateUserDto create(CreateUserDto createUserDto) {
        if(createUserDto!=null){
            UserEntity userEntity=new UserEntity();
            userEntity.setEmail(createUserDto.getEmail());
            userEntity.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
            userRepository.save(userEntity);
            CreateUserDto createUser=modelMapperService.forRequest().map(userEntity, CreateUserDto.class);
            return createUser;
        }
        return null;
    }

    @Override
    public CreateUserDto login(CreateUserDto createUserDto) {
        UserEntity userEntity=userRepository.findByEmail(createUserDto.getEmail());
        if (userEntity!=null&&passwordEncoder.matches(createUserDto.getPassword(),userEntity.getPassword())){
            CreateUserDto createUser=modelMapperService.forRequest().map(userEntity,CreateUserDto.class);
            return createUser;
        }else {
            return null;
        }
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, int id) {
        UserEntity userEntity=userRepository.findById(id).orElse(null);
        if(userEntity!=null&&passwordEncoder.matches(updateUserDto.getOldPassword(),userEntity.getPassword())){
            userEntity.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            userEntity.setEmail(updateUserDto.getEmail());
            userRepository.save(userEntity);
            customerRepository.updateCustomerMail(userEntity.getEmail(),id);
            UpdateUserDto updateUser=modelMapperService.forRequest().map(userEntity,UpdateUserDto.class);
            return updateUser;
        }
        return null;

    }

    @Override
    public boolean delete(int id) {
        UserEntity userEntity=userRepository.findById(id).orElse(null);
        if(userEntity!=null){
            userRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
