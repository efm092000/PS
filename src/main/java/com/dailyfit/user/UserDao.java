package com.dailyfit.user;

import java.util.Optional;

public interface UserDao {
    void createUser(User user);
    Optional<User> readUser(String email);
    void updateUser(User user);
    void deleteUser(User user);
}
