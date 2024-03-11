package com.dailyfit.user.service;

import com.dailyfit.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email) throws SQLException;
    User createUser(String email, String password, String name);
    User updateUser(User user);
    void deleteUser(String email);
}
