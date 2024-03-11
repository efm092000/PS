package com.dailyfit.routine.dao;

import com.dailyfit.routine.Routine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class RoutineDaoImpl implements RoutineDao{

    private final Connection connection;

    public RoutineDaoImpl(@Qualifier("dataBase") Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createRoutine(Routine routine) {

    }

    @Override
    public Optional<Routine> readRoutine(int rid) throws SQLException {
        try (ResultSet resultSet = queryRoutine(rid)) {
            String name = resultSet.getString("name");
            if (name != null) return Optional.of(new Routine(rid, name));
        }

        return Optional.empty();
    }

    @Override
    public void updateRoutine(int rid) {

    }

    @Override
    public void deleteRoutine(int rid) {

    }

    private ResultSet queryRoutine(int rid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT name FROM routine WHERE rid = '%s'", rid));
    }
}
