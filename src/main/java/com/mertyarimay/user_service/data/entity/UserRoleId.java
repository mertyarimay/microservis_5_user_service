package com.mertyarimay.user_service.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserRoleId implements Serializable {
    @Column(name="user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;

}
