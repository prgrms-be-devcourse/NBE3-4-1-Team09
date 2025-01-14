package com.nbe3.cafemanagement.service;


import com.nbe3.cafemanagement.domain.Admin;
import com.nbe3.cafemanagement.dto.AdminDto;
import com.nbe3.cafemanagement.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .roles("ADMIN")  // 권한 설정
                .build();
    }

    // 회원가입 처리 메서드
    @Transactional
    public boolean register(AdminDto adminDto) {
        Optional<Admin> existingAdmin = adminRepository.findByUsername(adminDto.getUsername());

        // 동일한 username이 이미 존재하는 경우 false 반환
        if (existingAdmin.isPresent()) {
            return false;
        }

        // 새로운 Admin 생성 및 비밀번호 암호화 후 저장
        Admin admin = new Admin();
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        adminRepository.save(admin);
        return true;
    }


    // 관리자 등록 시 비밀번호 암호화
    public void registerAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }
}