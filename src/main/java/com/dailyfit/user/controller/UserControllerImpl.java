package com.dailyfit.user.controller;

import com.dailyfit.user.User;
import com.dailyfit.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/{email}")
    public ResponseEntity<User> createUser(@PathVariable String email,
                                           @RequestParam String password,
                                           @RequestParam String name) {
        System.out.println("User created" + email);
        User user;
        try {
            user = userService.createUser(email, password, name);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> optionalUser = userService.getUserByEmail(email);
            return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = "/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) String name) {
        User user;
        try {
            user = userService.updateUser(email, password, name);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(user);
    }


    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok().build();
    }
}
