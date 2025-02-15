package com.mertyarimay.user_service.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users_roles")
public class users_roles {
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    private UserEntity user;

    @ManyToOne
    @MapsId("roleId")
    private RoleEntity role;
}
