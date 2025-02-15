package com.mertyarimay.user_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)  //@PreAuthorize()  kullanabiliyoruz bu annotation sayesinde
public class PasswordEncoderConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public PasswordEncoderConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF devre dışı bırakma
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/user/api/register").permitAll() // Kayıt için herkes erişebilir
                        .requestMatchers("/users/roles/api/create").permitAll() // Role Eklemek için herkes erişebilir
                        .requestMatchers( "/user/api/login").permitAll() // giriş için herkes erişebilir
                        .requestMatchers("/customer/api/create").permitAll() // customer kayıt için herkes erişebilir
                        .requestMatchers("/customer/api/update/{id}").hasAuthority("ROLE_CUSTOMER") // ROLE_CUSTOMER erişebilir
                        .requestMatchers("/user/api/update/{id}").hasAnyAuthority("ROLE_USER","ROLE_CUSTOMER") //Hem user hem customer erişir
                        .requestMatchers("/user/api/delete/{id}").hasAnyAuthority("ROLE_USER","ROLE_CUSTOMER") //Hem user hem customer erişir
                        .requestMatchers("/customer/api/getById/{id}").hasAuthority("ROLE_CUSTOMER") //Kendi ıdli alanına role customer ulaşssın
                        .requestMatchers("/customer/api/getAll").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/role/api/create").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated() // Diğer tüm isteklere kimlik doğrulama zorunlu
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtresi ekliyoruz
        //jwtAuthenticationFilter ilk önce bu filtre uygulanır tokenen doğruluğu kontrol edilir ,UsernamePasswordAuthenticationFilter.class kimlik doğrulaması için kullanılır login işlemlerinde

        return http.build(); // Yapılandırmayı tamamla ve SecurityFilterChain nesnesini döndür
    }






    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
