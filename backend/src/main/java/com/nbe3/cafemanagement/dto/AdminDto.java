package com.nbe3.cafemanagement.dto;

public class AdminDto {

    private String username;
    private String password;

    // 기본 생성자
    public AdminDto() {}

    // 생성자
    public AdminDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}