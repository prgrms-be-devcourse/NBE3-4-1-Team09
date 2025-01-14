package com.nbe3.cafemanagement.service;


import com.nbe3.cafemanagement.domain.Admin;
import com.nbe3.cafemanagement.dto.AdminDto;
import com.nbe3.cafemanagement.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean login(AdminDto adminDto) {
        // username으로 Admin을 조회
        Optional<Admin> optionalAdmin = adminRepository.findByUsername(adminDto.getUsername());

        // Admin이 존재하고, 패스워드가 일치하면 로그인 성공
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            return admin.getPassword().equals(adminDto.getPassword());
        }

        // 조회된 Admin이 없거나 패스워드가 일치하지 않으면 로그인 실패
        return false;
    }
}