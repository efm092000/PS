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
    public Routine createRoutine(String name, String email) throws SQLException {
        try (ResultSet resultSet = sqlQueryCreateRoutine(name, email)) {
            if (resultSet.next()) {
                int rid = resultSet.getInt(1);
                return new Routine(rid, name, email);
            }
            return null;
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

    @Override
    public RoutineExerciseDTO addExerciseToRoutine(int rid, String name, int sets, int reps) throws SQLException {
        sqlAddExerciseToRoutine(rid, name, sets, reps);
        return new RoutineExerciseDTO(rid, name, sets, reps);
    }

    @Override
    public void deleteExerciseFromRoutine(int rid, String name, int sets, int reps) throws SQLException {
        sqlDeleteExerciseFromRoutine(rid, name, sets, reps);
    }

    private ResultSet sqlQueryRoutine(int rid) throws SQLException {
        return connection.createStatement().executeQuery(String.format("SELECT name, email FROM routine WHERE rid = %d", rid));
    }

    private ResultSet sqlQueryCreateRoutine(String name, String email) throws SQLException {
        return connection.createStatement().executeQuery(String.format("INSERT INTO routine (name, email) VALUES ('%s', '%s') RETURNING last_insert_rowid()", name, email));
    }

    private void sqlUpdateRoutine(Routine routine) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE routine SET name = '%s' WHERE rid = %d", routine.name(), routine.rid()));
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

    private void sqlAddExerciseToRoutine(int rid, String name, int sets, int reps) throws SQLException {
        connection.createStatement().execute(String.format("INSERT INTO exercise_routine (rid, name, sets, reps) VALUES (%d, '%s', %d, %d)", rid, name, sets, reps));
    }

    private void sqlDeleteExerciseFromRoutine(int rid, String name, int sets, int reps) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM exercise_routine WHERE rid = %d AND name = '%s' AND sets = %d AND reps = %d", rid, name, sets, reps));
    }
}
