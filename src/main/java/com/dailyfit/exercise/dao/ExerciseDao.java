package com.dailyfit.exercise.dao;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface ExerciseDao {
    void createExercise(Exercise exercise) throws SQLException;
    Optional<String> readExercise(String name) throws SQLException;
    void updateExercise(Exercise exercise) throws SQLException;
    void deleteExercise(String name) throws SQLException;

    Optional<String> readExercises(String muscleGroup, String type, String difficulty, String material) throws SQLException;
}
