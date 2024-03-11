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
        try (ResultSet resultSet = sqlQueryUser(user.email())) {
            if (!resultSet.next()) {
                sqlCreateUser(user);
                return;
            }
            System.out.println("User exists");
        } catch (SQLException e) {
            throw new RuntimeException("Database error");
        }
    }

    @Override
    public Optional<User> readUser(String email) throws SQLException {
        try (ResultSet resultSet = sqlQueryUser(email)) {
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

    private ResultSet sqlQueryUser(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT password, name FROM user WHERE email = '%s'", email));
    }

    private void sqlCreateUser(User user) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO user (email, password, name) VALUES ('%s', '%s', '%s')", user.email(), user.password(), user.name()));
    }
}
