package com.dailyfit.user.controller;

import com.dailyfit.user.User;

public interface UserController {
    User getUserByEmail(String email);
}
