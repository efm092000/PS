package com.dailyfit.user.dao;

import com.dailyfit.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    void createUser(User user) throws SQLException;
    Optional<User> readUser(String email) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(String email) throws SQLException;

    void updatePassword(String email, String password) throws  SQLException;

    void updateName(String email, String name)throws SQLException;
}
