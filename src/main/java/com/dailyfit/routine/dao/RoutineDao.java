package com.dailyfit.routine.dao;

import com.dailyfit.routine.Routine;

import java.sql.SQLException;
import java.util.Optional;

public interface RoutineDao {
    void createRoutine(Routine routine);
    Optional<Routine> readRoutine(int rid) throws SQLException;
    void updateRoutine(int rid);
    void deleteRoutine(int rid);
}
