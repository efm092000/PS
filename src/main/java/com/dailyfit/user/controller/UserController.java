package com.dailyfit.user.controller;

import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<?> getUserByEmail(String email);
}
