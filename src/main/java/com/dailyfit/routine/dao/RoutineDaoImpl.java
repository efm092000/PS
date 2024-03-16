package com.dailyfit.routine.dao;

import com.dailyfit.routine.Routine;
import com.dailyfit.routine.RoutineExerciseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            String email = resultSet.getString("email");
            if (name != null) return Optional.of(new Routine(rid, name, email));
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

    @Override
    public List<Routine> getUserRoutines(String email) throws SQLException {
        try (ResultSet resultSet = sqlQueryUserRoutines(email)) {
            List<Routine> routines = new ArrayList<>();
            while (resultSet.next()) {
                int rid = resultSet.getInt("rid");
                String name = resultSet.getString("name");
                routines.add(new Routine(rid, name, email));
            }
            return routines;
        }
    }

    @Override
    public List<RoutineExerciseDTO> getRoutineExercises(int rid) throws SQLException {
        try (ResultSet resultSet = sqlQueryRoutineExercises(rid)) {
            List<RoutineExerciseDTO> routineExerciseDTOList = new ArrayList<>();
            while (resultSet.next()) {
                String exercise = resultSet.getString("name");
                int sets = resultSet.getInt("sets");
                int reps = resultSet.getInt("reps");
                routineExerciseDTOList.add(new RoutineExerciseDTO(rid, exercise, sets, reps));
            }
            return routineExerciseDTOList;
        }
    }

    private ResultSet sqlQueryRoutine(int rid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT name, email FROM routine WHERE rid = %d", rid));
    }

    private void sqlCreateRoutine(Routine routine) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO routine (rid, name, email) VALUES (%s, '%s', '%s')", routine.rid(), routine.name(), routine.email()));
    }

    private void sqlUpdateRoutine(Routine routine) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE routine SET name = '%s', email = '%s' WHERE rid = %d", routine.name(), routine.email(), routine.rid()));
    }

    private void sqlDeleteRoutine(int rid) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM routine WHERE rid = %d", rid));
    }

    private ResultSet sqlQueryUserRoutines(String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT rid, name FROM routine WHERE email = '%s'", email));
    }

    private ResultSet sqlQueryRoutineExercises(int rid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT * FROM exercise_routine WHERE rid = %d", rid));
    }
}
