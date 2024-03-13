package com.dailyfit.exercise.service;

import com.dailyfit.exercise.Exercise;

import java.sql.SQLException;
import java.util.Optional;

public interface ExerciseService {
    Optional<String> getExerciseByQuery(String name) throws SQLException;
    Optional<String> getExercisesByQuery(String muscleGroup, String type, String difficulty, String material) throws SQLException;
    void createExercise(Exercise exercise) throws SQLException;
    void updateExercise(Exercise exercise) throws SQLException;
    void deleteExercise(String name) throws SQLException;
}
