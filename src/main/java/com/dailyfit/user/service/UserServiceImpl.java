package com.dailyfit.user.service;

import com.dailyfit.user.ResourceDTO;
import com.dailyfit.user.User;
import com.dailyfit.user.dao.UserDao;
import com.dailyfit.user.exception.FileNotFoundException;
import com.dailyfit.user.exception.FileNotSupportedException;
import com.dailyfit.user.exception.UserAlreadyExistsException;
import com.dailyfit.user.exception.UserNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

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
        User user = new User(email, password, name, false, false, "profile-picture/icon.png");
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
    public String handleFileUpload(String email, MultipartFile file) {

        try {

            String fileName = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();
            long fileSize = file.getSize();
            long maxFileSize = 10 * 1024 * 1024;

            if (fileSize > maxFileSize) {
                return ("File size must be less then or equal 10MB");
            }

            assert fileOriginalName != null;
            if (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png")) {
                return ("Only JPG, JPEG, PNG files are allowed");
            }

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;

            File folder = new File("src/main/resources/profile-pictures");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Path path = Paths.get("src/main/resources/profile-pictures/" + newFileName);
            Files.write(path, bytes);
            userDao.updateProfilePicture(email, newFileName);

        } catch (Exception e) {
            return ("Error uploading file");
        }

        return ("File has been uploaded");
    }

    public ResourceDTO getProfilePicture(String email) throws UserNotFoundException, FileNotFoundException, SQLException, FileNotSupportedException {

        Optional<User> optionalUser = getUserByEmail(email);
        if (optionalUser.isPresent()) {
            String profilePicturePath = optionalUser.get().profilePicture();
            Path path = new File("src/main/resources/profile-pictures/" + profilePicturePath).toPath();
            FileSystemResource resource = new FileSystemResource(path);
            if (resource.exists() && resource.isReadable()) {
                MediaType mediaType;
                if (profilePicturePath.endsWith(".jpg") || profilePicturePath.endsWith(".jpeg")) {
                    mediaType = MediaType.IMAGE_JPEG;
                } else if (profilePicturePath.endsWith(".png")) {
                    mediaType = MediaType.IMAGE_PNG;
                } else {
                    throw new FileNotSupportedException();
                }
                return new ResourceDTO(mediaType, resource);
            } else {
                throw new FileNotFoundException();
            }

        }
        throw new UserNotFoundException(email);
    }

}
