package com.mertyarimay.user_service.data.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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


    @ManyToMany
    @JoinTable(name = "users_roles", // 2 tablo arasındaki ilişkiyi temsil eder.
            joinColumns = @JoinColumn(name = "user_id"), //users tablosunu temsil eder
            inverseJoinColumns = @JoinColumn(name = "role_id"))//ilişki kurduğu tabloyu temsil eder.
    private Set<RoleEntity>roles;



}
