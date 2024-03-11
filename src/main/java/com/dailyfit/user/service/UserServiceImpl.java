package com.dailyfit.user.service;

import com.dailyfit.user.User;
import com.dailyfit.user.dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByEmail(String email) {

        Optional<User> user = userDao.readUser(email);
        return user.orElse(new User("Not Found", "Not Found", "Not Found"));
    }

    @Override
    public User createUser(User user) {
        //TODO UserServiceImpl.createUser
        return null;
    }

    @Override
    public User updateUser(User user) {
        //TODO UserServiceImpl.updateUser
        return null;
    }

    @Override
    public void deleteUser(String email) {
        //TODO UserServiceImpl.deleteUser
    }
}
