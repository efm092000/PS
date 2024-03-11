package com.dailyfit.user.controller;

import com.dailyfit.user.User;
import com.dailyfit.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> optionalUser = userService.getUserByEmail(email);
            return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
