package com.dailyfit.user.dao;

import com.dailyfit.user.User;
import com.dailyfit.user.exception.UserAlreadyExistsException;
import com.dailyfit.user.exception.UserNotFoundException;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    void createUser(User user) throws SQLException, UserAlreadyExistsException;
    Optional<User> readUser(String email) throws SQLException;
    void updateUser(User user) throws SQLException, UserNotFoundException;
    void deleteUser(String email) throws SQLException, UserNotFoundException;
}
