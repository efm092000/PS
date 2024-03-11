package com.dailyfit.routine.service;

import com.dailyfit.routine.Routine;

import java.sql.SQLException;
import java.util.Optional;

public interface RoutineService {
    Optional<Routine> getRoutineByRid(int rid) throws SQLException;
    Routine createRoutine(Routine routine);
    Routine updateRoutine(Routine routine);
    void deleteRoutine(int rid);
}
