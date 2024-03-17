package com.dailyfit.exercise.dao;

import com.dailyfit.exercise.Exercise;

import java.sql.SQLException;
import java.util.List;
public interface ExerciseDao {
    void createExercise(Exercise exercise) throws SQLException;
    List<Exercise> readExercise(String name) throws SQLException;
    List<Exercise> readExercises(String muscleGroup, String type, Integer difficulty, Boolean material) throws SQLException;

    void updateExercise(Exercise exercise) throws SQLException;
    void deleteExercise(String name) throws SQLException;

}
