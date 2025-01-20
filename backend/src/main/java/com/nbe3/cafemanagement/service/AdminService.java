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
public class AdminService implements UserDetailsService, FailedLoginService {

    private static final int MAX_FAILED_ATTEMPTS = 3; // 최대 실패 횟수
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("ID를 찾을 수 없습니다: " + username));

        if (admin.isBlocked()) {
            throw new RuntimeException("계정이 블록되었습니다. ");
        }

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .roles("ADMIN")  // 권한 설정
                .build();
    }

    // 로그인 실패 시 실패 횟수 증가
    @Override
    @Transactional
    public void increaseFailedAttempts(String username) {
        adminRepository.findByUsername(username).ifPresent(admin -> {
            int attempts = admin.getFailedAttempts() + 1;
            admin.setFailedAttempts(attempts);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                admin.setBlocked(true); // 계정 블록 처리
            }

            adminRepository.save(admin);
        });
    }

    // 로그인 성공 시 실패 횟수 초기화
    public void resetFailedAttempts(String username) {
        adminRepository.findByUsername(username).ifPresent(admin -> {
            admin.setFailedAttempts(0); // 실패 횟수 초기화
            adminRepository.save(admin); // 데이터베이스에 저장
        });
    }

    // 계정이 블록 상태인지 확인
    public boolean isAccountBlocked(String username) {
        boolean blocked = adminRepository.findByUsername(username)
                .map(Admin::isBlocked)
                .orElse(false);
        System.out.println("Account blocked status for " + username + ": " + blocked);
        return blocked;
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