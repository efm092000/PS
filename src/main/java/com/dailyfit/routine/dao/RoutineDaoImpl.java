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
    public void createRoutine(Routine routine) throws SQLException {
        try (ResultSet resultSet = sqlQueryRoutine(routine.rid())) {
            if (!resultSet.next()) {
                sqlCreateRoutine(routine);
            }

            // TODO: Print error when routine already exists
        }
    }

    @Override
    public Optional<Routine> readRoutine(int rid) throws SQLException {
        try (ResultSet resultSet = sqlQueryRoutine(rid)) {
            String name = resultSet.getString("name");
            if (name != null) return Optional.of(new Routine(rid, name));
        }

        return Optional.empty();
    }

    @Override
    public void updateRoutine(Routine routine) throws SQLException {
        try (ResultSet resultSet = sqlQueryRoutine(routine.rid())) {
            if (resultSet.next()) {
                sqlUpdateRoutine(routine);
            }
        }

        // TODO: Print error when routine doesn't exists
    }

    @Override
    public void deleteRoutine(int rid) throws SQLException {
        try (ResultSet resultSet = sqlQueryRoutine(rid)){
            if (resultSet.next()) {
                sqlDeleteRoutine(rid);
            }
        }

        // TODO: Print error when routine doesn't exists
    }

    private ResultSet sqlQueryRoutine(int rid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT name FROM routine WHERE rid = %d", rid));
    }

    private void sqlCreateRoutine(Routine routine) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO routine (rid, name) VALUES (%s, '%s')", routine.rid(), routine.name()));
    }

    private void sqlUpdateRoutine(Routine routine) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE routine SET name = '%s' WHERE rid = %d", routine.name(), routine.rid()));
    }

    private void sqlDeleteRoutine(int rid) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM routine WHERE rid = %d", rid));
    }
}
