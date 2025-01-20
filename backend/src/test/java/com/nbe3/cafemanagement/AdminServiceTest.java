package com.nbe3.cafemanagement;


import com.nbe3.cafemanagement.domain.Admin;
import com.nbe3.cafemanagement.repository.AdminRepository;
import com.nbe3.cafemanagement.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 초기화
        adminRepository.deleteAll();

        // 기본 Admin 사용자 추가
        Admin admin = new Admin();
        admin.setUsername("testAdmin");
        admin.setPassword(passwordEncoder.encode("password123")); // 암호화된 비밀번호 저장
        adminRepository.save(admin);
    }

    @Test// 로그인 성공 케이스
    void loginSuccess() {

        System.out.println("[Test] 정상적인 사용자 로그인 테스트 시작: testAdmin");

        UserDetails userDetails = adminService.loadUserByUsername("testAdmin");

        // 반환된 UserDetails 검증
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("testAdmin");
        assertThat(passwordEncoder.matches("password123", userDetails.getPassword())).isTrue();

        System.out.println("[Success] 사용자 'testAdmin'에 대한 로그인 성공!");
        System.out.println("[Details] UserDetails 반환 값: " + userDetails);
    }

    @Test // 로그인 실패 케이스
    void loginFail_UserNotFound() {
        String unknownUsername = "unknownUser";
        System.out.println("[Test] 존재하지 않는 사용자 로그인 테스트 시작: " + unknownUsername);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> adminService.loadUserByUsername(unknownUsername));

        System.out.println("[예상 예외] " + exception.getMessage());
        System.out.println("[Success] 존재하지 않는 사용자 '" + unknownUsername + "'에 대해 사용자 이름없음의 예외가 발생했습니다.");
    }

    @Test // 로그인 실패 블록 테스트
    void loginFail_AccountBlocked() {
        System.out.println("[Test] 블록된 사용자 로그인 테스트 시작: testAdmin");

        // 계정이 블록된 사용자 추가
        Admin blockedAdmin = adminRepository.findByUsername("testAdmin").orElseThrow();
        blockedAdmin.setBlocked(true);
        adminRepository.save(blockedAdmin);
        System.out.println("[Setup] 사용자 'testAdmin'의 계정이 블록 처리되었습니다.");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.loadUserByUsername("testAdmin"));

        System.out.println("[예상 예외] " + exception.getMessage());
        assertThat(exception.getMessage()).isEqualTo("계정이 블록되었습니다. ");
        System.out.println("[Success] 블록된 사용자 'testAdmin'에 대해 올바른 예외가 발생했습니다.");
    }
}