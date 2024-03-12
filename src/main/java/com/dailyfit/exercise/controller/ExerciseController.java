package com.dailyfit.exercise.controller;

import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public interface ExerciseController {
    ResponseEntity<String> getExercisesByQuery(String muscleGroup,
                                          String type,
                                          String name,
                                          String difficulty,
                                          String material);
    void deleteExercise(String name) throws SQLException;
    void addExercisesByQuery(String muscleGroup,
                             String type,
                             String name,
                             String difficulty,
                             String material) throws SQLException;
    void updateExercisesByQuery(String muscleGroup,
                                String type,
                                String name,
                                String difficulty,
                                String material) throws SQLException;
}
