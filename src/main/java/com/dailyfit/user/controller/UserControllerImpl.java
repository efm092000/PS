package com.dailyfit.user.controller;

import com.dailyfit.user.User;
import com.dailyfit.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
