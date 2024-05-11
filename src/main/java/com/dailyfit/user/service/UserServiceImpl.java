package com.dailyfit.user.service;

import com.dailyfit.user.User;
import com.dailyfit.user.dao.UserDao;
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
        User user = new User(email, password, name, false, false, "http://localhost:8080/images/icon.png");
        userDao.createUser(user);
        return user;
    }

    @Override
    public User updateUser(String email, String password, String name, boolean premium) throws SQLException, UserNotFoundException {
        Optional<User> user1 = userDao.readUser(email);
        if (user1.isPresent()) {
            if (password == null && user1.get().premium() == premium) {
                userDao.updateName(email, name);
            }
            if (name == null && user1.get().premium() == premium) {
                userDao.updatePassword(email, password);
            }
            if (user1.get().premium() != premium) {
                userDao.updatePremium(email, premium);
            }
            Optional <User> updatedUser = userDao.readUser(email);
            if(updatedUser.isPresent()){
                return updatedUser.get();
            }
        }
        throw new UserNotFoundException(email);
    }

    @Override
    public void deleteUser(String email) throws SQLException {
        /* TODO UserServiceImpl.deleteUser
         * 1. Check if user exists
         */
        userDao.deleteUser(email);
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) throws SQLException {
        if (email == null || password == null) {
            return Optional.empty();
        }
        Optional<User> userOptional = userDao.readUser(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.password().equals(password)) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateProfilePicture(String email, String imageUrl) throws SQLException {
        userDao.updateProfilePicture(email, imageUrl);
    }

}
