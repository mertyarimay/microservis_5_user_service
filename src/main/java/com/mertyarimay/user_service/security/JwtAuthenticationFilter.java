package com.mertyarimay.user_service.security;

import com.mertyarimay.user_service.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
// gelen isteklerin  üzerinde kimlik doğrulama ve yetkilendirme işlemi yapar bu sınıf
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().equals("/user/api/register") || request.getRequestURI().equals("/user/api/login")|| request.getRequestURI().equals("/users/roles/api/create")|| request.getRequestURI().equals("/customer/api/create")) {
            filterChain.doFilter(request, response);
            return; //her gelen istekte yetki kontrolü yapar ama üstteki url leri atla bir işlem yapma
        }

        String token = getTokenFromRequest(request);  // Bearer token'ı alır

        if (token != null && jwtUtil.validateToken(token)) {  // Token geçerli mi kontrol edilir
            String username = jwtUtil.extractUsername(token); // Token'dan username çıkarılır
            List<String> roles = jwtUtil.extractRoles(token);  // Token'dan roller alınır
            String userIdFromToken = jwtUtil.extractUserId(token);//tokendan kullanıcı ıd si çıkarılır

            if (roles == null || roles.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // eğer roller boş ise 403 dön
                return;
            }
            // URL'deki ID'yi dinamik olarak bulmak
           /* String[] urlSegments = request.getRequestURI().split("/");
            // URL'deki her segmenti kontrol et
            for (String segment : urlSegments) {
                if (segment.matches("\\d+")) {  // Eğer segment sayısal bir değerse (ID)
                    String pathId = segment;  // Segmenti ID olarak al
                    if (!userIdFromToken.equals(pathId)) {  // Token'daki kullanıcı ID'si ile karşılaştır
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 Forbidden döndür
                        return;
                    }
                    break;  // ID bulunduktan sonra döngüyü bitir
                }
            }*/

            // Rolleri "ROLE_" ile prefixli şekilde set ediyoruz  çünkü gelen roller bu şekilde başlaması lazım
            List<String> authorities = roles.stream()
                    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role) // Eğer başında ROLE_ yoksa ekle
                    .collect(Collectors.toList());


            // UsernamePasswordAuthenticationToken ile kimlik doğrulaması yapılır security de
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.createAuthorityList(authorities.toArray(new String[0])));

            // Spring Security Context'e kullanıcı bilgileri set edilir  doğrulama başarılı ise  kullanıcı bilgilerini  güvenlik bağlamına yerleştirir
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //doğrulama hataylıysa token geçersizsse 401 dön

        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Diğer filtreler çalışmaya devam eder
        filterChain.doFilter(request, response);
    }

    // Authorization header'ından token'ı çıkarma metodu
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " kısmını çıkartıp token'ı döndür
        }
        return null;
    }
}

