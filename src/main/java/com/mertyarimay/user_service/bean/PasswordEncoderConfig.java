package com.mertyarimay.user_service.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@EnableWebSecurity
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Kayıt işlemi için sadece /register endpoint'ine erişimi izin ver
        http.csrf(csrf -> csrf.disable())  // CSRF'yi devre dışı bırak
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/api/register","/user/api/login","/customer/api/create","/user/api/update","/user/api/delete/{id}").permitAll()
                                .anyRequest().permitAll()  // Diğer tüm istekler için kimlik doğrulaması gerekmez
                );

        return http.build();
    }
}
