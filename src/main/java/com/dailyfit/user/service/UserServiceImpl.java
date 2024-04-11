package com.dailyfit.user.service;

import com.dailyfit.user.User;
import com.dailyfit.user.dao.UserDao;
import com.dailyfit.user.exception.InvalidCredentialsException;
import com.dailyfit.user.exception.UserAlreadyExistsException;
import com.dailyfit.user.exception.UserNotFoundException;
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
    public User updateUser(String email, String password, String name) throws SQLException, UserNotFoundException {
        if (password == null || name == null) {
            throw new IllegalArgumentException("Password and name cannot be null");
        }
        if (userDao.readUser(email).isEmpty()) {
            throw new UserNotFoundException(email);
        }
        User user = new User(email, password, name);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public void deleteUser(String email) throws SQLException, UserNotFoundException {
        Optional<User> user = userDao.readUser(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        userDao.deleteUser(email);
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) throws SQLException, InvalidCredentialsException {
        if (email == null || password == null) {
            return Optional.empty();
        }
        Optional<User> userOptional = userDao.readUser(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.password().equals(password)) {
                return userOptional;
            }
            throw new InvalidCredentialsException();
        }
        return Optional.empty();
    }
}
