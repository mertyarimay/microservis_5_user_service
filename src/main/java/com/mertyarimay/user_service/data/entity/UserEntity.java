package com.mertyarimay.user_service.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_ınformation")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name="password")
    private String password;

    @OneToOne(mappedBy ="userEntity",cascade = CascadeType.ALL,orphanRemoval = true)
    private CustomerEntity customerEntity;

}
