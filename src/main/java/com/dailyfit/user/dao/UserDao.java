package com.dailyfit.user.dao;

import com.dailyfit.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    void createUser(User user);
    Optional<User> readUser(String email) throws SQLException;
    void updateUser(User user);
    void deleteUser(User user);
}
