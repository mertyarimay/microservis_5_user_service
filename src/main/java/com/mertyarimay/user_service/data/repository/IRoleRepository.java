package com.mertyarimay.user_service.data.repository;

import com.mertyarimay.user_service.data.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity,Integer> {
}
