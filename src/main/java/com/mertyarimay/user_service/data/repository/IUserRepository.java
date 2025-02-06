package com.mertyarimay.user_service.data.repository;

import com.mertyarimay.user_service.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity,Integer> {

   UserEntity findByEmail(String email);
}
