package com.dailyfit.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public Optional<User> readUser(String email) {
        try {
            ResultSet resultSet = queryUser(email);
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            return Optional.of(new User(email, password, name));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    private ResultSet queryUser(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT password, name FROM users WHERE email = '%s'", email));
    }
}
