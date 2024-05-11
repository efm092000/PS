package com.dailyfit.user.service;

import com.dailyfit.user.ResourceDTO;
import com.dailyfit.user.User;
import com.dailyfit.user.exception.FileNotFoundException;
import com.dailyfit.user.exception.FileNotSupportedException;
import com.dailyfit.user.exception.UserAlreadyExistsException;
import com.dailyfit.user.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email) throws SQLException;
    User createUser(String email, String password, String name) throws SQLException, UserAlreadyExistsException;
    User updateUser(String email, String password, String name, boolean premium) throws SQLException, UserNotFoundException;
    void deleteUser(String email) throws SQLException;
    Optional<User> authenticateUser(String email, String password) throws SQLException;
    void updateProfilePicture(String email, String imageUrl) throws SQLException;
}
