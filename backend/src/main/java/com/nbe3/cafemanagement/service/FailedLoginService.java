package com.nbe3.cafemanagement.service;

public interface FailedLoginService {
    void increaseFailedAttempts(String username);
    boolean isAccountBlocked(String username);
}