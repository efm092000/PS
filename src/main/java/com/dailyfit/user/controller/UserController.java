package com.dailyfit.user.controller;

import com.dailyfit.user.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<UserDTO> getUserByEmail(String email);
}
