package com.mertyarimay.user_service.data.repository;

import com.mertyarimay.user_service.data.entity.UserRoleId;
import com.mertyarimay.user_service.data.entity.users_roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRolesRepository extends JpaRepository<users_roles, UserRoleId> {
}
