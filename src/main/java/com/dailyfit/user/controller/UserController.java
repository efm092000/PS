package com.dailyfit.user.controller;

import com.dailyfit.user.User;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<User> getUserByEmail(String email);
}
