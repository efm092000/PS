package com.dailyfit.routine.dao;

import com.dailyfit.routine.Routine;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoutineDao {
    void createRoutine(Routine routine) throws SQLException;
    Optional<Routine> readRoutine(int rid) throws SQLException;
    void updateRoutine(Routine routine) throws SQLException;
    void deleteRoutine(int rid) throws SQLException;

    List<Routine> getUserRoutines(String email) throws SQLException;
}
