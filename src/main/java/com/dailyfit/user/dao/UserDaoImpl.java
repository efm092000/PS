package com.dailyfit.user.dao;

import com.dailyfit.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    private final Connection connection;

    public UserDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUser(User user) {
        //TODO UserDaoImpl.createUser
    }

    @Override
    public Optional<User> readUser(String email) throws SQLException {
        try (ResultSet resultSet = queryUser(email)) {
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            if (password != null && name != null) {
                return Optional.of(new User(email, password, name));
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {
        //TODO UserDaoImpl.updateUser
    }

    @Override
    public void deleteUser(User user) {
        //TODO UserDaoImpl.deleteUser
    }

    private ResultSet queryUser(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT password, name FROM users WHERE email = '%s'", email));
    }
}
