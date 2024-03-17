package com.dailyfit.exercise.service;

import com.dailyfit.exercise.Exercise;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    List<Exercise> getExerciseByQuery(String name) throws SQLException;
    List<Exercise> getExercisesByQuery(String muscleGroup, String type, Integer difficulty, Boolean material) throws SQLException;
    void createExercise(Exercise exercise) throws SQLException;
    void updateExercise(Exercise exercise) throws SQLException;
    void deleteExercise(String name) throws SQLException;
}
