package com.dailyfit.user.dao;

import com.dailyfit.user.User;
import com.dailyfit.user.exception.UserAlreadyExistsException;
import com.dailyfit.user.exception.UserNotFoundException;
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
    public void createUser(User user) throws SQLException, UserAlreadyExistsException {
        try (ResultSet resultSet = sqlQueryUser(user.email())) {
            if (!resultSet.next()) {
                sqlCreateUser(user);
                return;
            }
            throw new UserAlreadyExistsException(user.email());
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
    public void updateUser(User user) throws SQLException, UserNotFoundException {
        try (ResultSet resultSet = sqlQueryUser(user.email())) {
            if (resultSet.next()) {
                sqlUpdateUser(user);
                return;
            }
            throw new UserNotFoundException(user.email());
        }
    }

    @Override
    public void deleteUser(String email) throws SQLException, UserNotFoundException {
        try (ResultSet resultSet = sqlQueryUser(email)) {
            if (resultSet.next()) {
                sqlDeleteUser(email);
                return;
            }
            throw new UserNotFoundException(email);
        }
    }

    private ResultSet sqlQueryUser(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT password, name FROM \"user\" WHERE email = '%s'", email));
    }

    private void sqlCreateUser(User user) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO \"user\" (email, password, name) VALUES ('%s', '%s', '%s')", user.email(), user.password(), user.name()));
    }

    private void sqlDeleteUser(String email) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM \"user\" WHERE email = '%s'", email));
    }

    private void sqlUpdateUser(User user) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE \"user\" SET password = '%s', name = '%s' WHERE email = '%s'", user.password(), user.name(), user.email()));
    }
}
