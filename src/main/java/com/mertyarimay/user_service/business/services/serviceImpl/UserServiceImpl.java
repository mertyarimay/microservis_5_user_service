package com.mertyarimay.user_service.business.services.serviceImpl;

import com.mertyarimay.user_service.business.dto.CreateUserDto;
import com.mertyarimay.user_service.business.dto.UpdateUserDto;
import com.mertyarimay.user_service.business.dto.UserLoginResponse;
import com.mertyarimay.user_service.business.services.service.UserService;
import com.mertyarimay.user_service.data.entity.UserEntity;
import com.mertyarimay.user_service.data.repository.ICustomerRepository;
import com.mertyarimay.user_service.data.repository.IUserRepository;
import com.mertyarimay.user_service.mappers.ModelMapperService;
import com.mertyarimay.user_service.security.JwtAuthenticationFilter;
import com.mertyarimay.user_service.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final IUserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;
    private final ICustomerRepository customerRepository;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


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
    public UserLoginResponse login(CreateUserDto createUserDto) {
        UserEntity userEntity=userRepository.findByEmail(createUserDto.getEmail());
        if (userEntity!=null&&passwordEncoder.matches(createUserDto.getPassword(),userEntity.getPassword())){
            UserLoginResponse userLoginResponse=modelMapperService.forResponse().map(userEntity,UserLoginResponse.class);
            List<String>roleNames=userEntity.getRoles().stream().map(roleEntity -> roleEntity.getRoleName()).collect(Collectors.toList());
            userLoginResponse.setRoleNames(roleNames);
            userLoginResponse.setId(String.valueOf(userEntity.getId()));
            return userLoginResponse;
        }else {
            return null;
        }
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, int id,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        UserEntity userEntity=userRepository.findByEmail(updateUserDto.getEmail());
        if(userEntity!=null&&passwordEncoder.matches(updateUserDto.getOldPassword(),userEntity.getPassword())){
            String tokenUserId=jwtUtil.extractUserId(token);
            int tokenId=Integer.parseInt(tokenUserId);
            if(id==tokenId){
                userEntity.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
                userEntity.setEmail(updateUserDto.getEmail());
                userRepository.save(userEntity);
                customerRepository.updateCustomerMail(userEntity.getEmail(),id);
                UpdateUserDto updateUser=modelMapperService.forRequest().map(userEntity,UpdateUserDto.class);
                return updateUser;
            }
        }
        return null;

    }

    @Override
    public boolean delete(int id,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        UserEntity userEntity=userRepository.findById(id).orElse(null);
        if(userEntity!=null){
            String tokenUserId=jwtUtil.extractUserId(token);
            int tokenId=Integer.parseInt(tokenUserId);
            if(id==tokenId){
                userRepository.deleteById(id);
                if(!userRepository.existsById(id)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
