package com.mertyarimay.user_service.data.repository;

import com.mertyarimay.user_service.data.entity.CustomerEntity;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICustomerRepository extends JpaRepository<CustomerEntity,Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE CustomerEntity c SET c.email=:email where c.userEntity.id=:id")
     void updateCustomerMail(@Param("email")String email, @Param("id")int id);


    boolean existsByPhoneNumber(String phoneNumber);
}
