package com.dailyfit.routine.dao;

import com.dailyfit.routine.Routine;
import com.dailyfit.routine.RoutineExerciseDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoutineDao {
    Routine createRoutine(String name, String email) throws SQLException;
    Optional<Routine> readRoutine(int rid) throws SQLException;
    void updateRoutine(Routine routine) throws SQLException;
    void deleteRoutine(int rid) throws SQLException;

    List<Routine> getUserRoutines(String email) throws SQLException;
    List<RoutineExerciseDTO> getRoutineExercises(int rid) throws SQLException;

    RoutineExerciseDTO addExerciseToRoutine(int rid, String name, int sets, int reps) throws SQLException;
}
