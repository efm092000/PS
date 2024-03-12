package com.dailyfit.routine.service;

import com.dailyfit.routine.Routine;

import java.sql.SQLException;
import java.util.Optional;

public interface RoutineService {
    Optional<Routine> getRoutineByRid(int rid) throws SQLException;
    Routine createRoutine(int rid, String name) throws SQLException;
    Routine updateRoutine(int rid, String name) throws SQLException;
    void deleteRoutine(int rid);
}
