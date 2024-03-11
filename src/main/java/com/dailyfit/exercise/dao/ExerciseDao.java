package com.dailyfit.exercise.dao;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface ExerciseDao {
    void createExercise(Exercise exercise);
    Optional<Exercise> readExercise(String muscleGroup, String type, String name, String difficulty, String material) throws SQLException;
    void updateExercise(Exercise exercise);
    void deleteExercise(Exercise exercise);
}
