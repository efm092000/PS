package com.dailyfit.user.service;

import com.dailyfit.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email) throws SQLException;
    User createUser(String email, String password, String name) throws SQLException;
    User updateUser(String email, String password, String name) throws SQLException;
    void deleteUser(String email) throws SQLException;
}
