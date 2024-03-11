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
    public void createUser(User user) throws SQLException {
        try (ResultSet resultSet = sqlQueryUser(user.email())) {
            if (!resultSet.next()) {
                sqlCreateUser(user);
                return;
            }
            System.err.println("User exists");
            // Throw exception
        }
    }

    @Override
    public Optional<User> readUser(String email) throws SQLException {
        ResultSet resultSet = sqlQueryUser(email);
        if (resultSet.next()) {
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            return Optional.of(new User(email, password, name));
        }
        resultSet.close();
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try (ResultSet resultSet = sqlQueryUser(user.email())) {
            if (resultSet.next()) {
                sqlUpdateUser(user);
                return;
            }
            System.err.println("User not found");
            // Throw exception
        }
    }

    @Override
    public void deleteUser(String email) throws SQLException {
        try (ResultSet resultSet = sqlQueryUser(email)) {
            if (resultSet.next()) {
                sqlDeleteUser(email);
            }
            System.err.println("User not found");
            // Throw exception
        }
    }

    private ResultSet sqlQueryUser(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT password, name FROM user WHERE email = '%s'", email));
    }

    private void sqlCreateUser(User user) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO user (email, password, name) VALUES ('%s', '%s', '%s')", user.email(), user.password(), user.name()));
    }

    private void sqlDeleteUser(String email) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM user WHERE email = '%s'", email));
    }

    private void sqlUpdateUser(User user) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE user SET password = '%s', name = '%s' WHERE email = '%s'", user.password(), user.name(), user.email()));
    }
}
