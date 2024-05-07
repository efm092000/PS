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
            int premiumInteger = resultSet.getInt("isPremium");
            boolean premium = premiumInteger == 1;
            return Optional.of(new User(email, password, name, premium));
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
                return;
            }
            System.err.println("User not found");
            // Throw exception
        }
    }

    @Override
    public void updatePassword(String email, String password) throws SQLException {
        try (ResultSet resultSet = sqlQueryUser(email)) {
            if (resultSet.next()) {
                sqlUpdatePassword(email,password);
                return;
            }
            System.err.println("User not found");
            // Throw exception
        }
    }
    @Override
    public void updateName(String email, String name) throws SQLException {
        try (ResultSet resultSet = sqlQueryUser(email)) {
            if (resultSet.next()) {
                sqlUpdateName(email,name);
                return;
            }
            System.err.println("User not found");
            // Throw exception
        }
    }

    @Override
    public void updatePremium(String email, boolean premium) throws SQLException {
        try (ResultSet resultSet = sqlQueryUser(email)) {
            if (resultSet.next()) {
                int value = !premium ? 0 : 1;
                sqlUpdatePremium(email,value);
                return;
            }
            System.err.println("User not found");
            // Throw exception
        }
    }

    private void sqlUpdateName(String email, String name) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE \"user\" SET name = '%s' WHERE email = '%s'", name, email));
    }

    private void sqlUpdatePassword(String email, String password) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE \"user\" SET password = '%s' WHERE email = '%s'", password, email));
    }

    private void sqlUpdatePremium(String email, int premium) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE \"user\" SET isPremium = %d WHERE email = '%s'", premium, email));
    }

    private ResultSet sqlQueryUser(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT password, name, isPremium FROM \"user\" WHERE email = '%s'", email));
    }

    private void sqlCreateUser(User user) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO \"user\" (email, password, name, isPremium) VALUES ('%s', '%s', '%s', '%b')", user.email(), user.password(), user.name(), false));
    }

    private void sqlDeleteUser(String email) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM \"user\" WHERE email = '%s'", email));
    }

    private void sqlUpdateUser(User user) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE \"user\" SET password = '%s', name = '%s', isPremium = '%b' WHERE email = '%s'", user.password(), user.name(), user.email(), user.premium()));
    }

}
