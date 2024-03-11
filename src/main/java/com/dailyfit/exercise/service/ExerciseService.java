package com.dailyfit.exercise.service;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface ExerciseService {
    Optional<Exercise> getExerciseByQuery(String muscleGroup, String type, String name, String difficulty, String material) throws SQLException;
    User createExercise(Exercise exercise);
    User updateExercise(Exercise exercise);
    void deleteExercise(String name);
}
