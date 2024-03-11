package com.dailyfit.user.service;

import com.dailyfit.user.User;

public interface UserService {
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(String email);
}
