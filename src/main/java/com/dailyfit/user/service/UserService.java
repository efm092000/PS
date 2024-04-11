package com.dailyfit.user.service;

import com.dailyfit.user.User;
import com.dailyfit.user.exception.UserAlreadyExistsException;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email) throws SQLException;
    User createUser(String email, String password, String name) throws SQLException, UserAlreadyExistsException;
    User updateUser(String email, String password, String name) throws SQLException;
    void deleteUser(String email) throws SQLException;
    Optional<User> authenticateUser(String email, String password) throws SQLException;
}
