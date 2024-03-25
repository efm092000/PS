package com.dailyfit.user.service;

import com.dailyfit.user.User;
import com.dailyfit.user.dao.UserDao;
import com.dailyfit.user.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws SQLException {
        return userDao.readUser(email);
    }

    @Override
    public User createUser(String email, String password, String name) throws SQLException, UserAlreadyExistsException {
        Optional<User> userOptional = userDao.readUser(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
        User user = new User(email, password, name);
        userDao.createUser(user);
        return user;
    }

    @Override
    public User updateUser(String email, String password, String name) throws SQLException {
        /* TODO UserServiceImpl.updateUser
        * 1. Check if user exists
        * 2. Check if password or name is null
         */
        User user = new User(email, password, name);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public void deleteUser(String email) throws SQLException {
        /* TODO UserServiceImpl.deleteUser
        * 1. Check if user exists
         */
        userDao.deleteUser(email);
    }
}
