package com.dailyfit.routine.service;

import com.dailyfit.routine.Routine;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoutineService {
    Optional<Routine> getRoutineByRid(int rid) throws SQLException;
    void createRoutine(String name, String email) throws SQLException;
    Routine updateRoutine(int rid, String name, String email) throws SQLException;
    void deleteRoutine(int rid) throws SQLException;
    List<Routine> getUserRoutines(String email) throws SQLException;
}
