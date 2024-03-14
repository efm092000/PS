package com.dailyfit.exercise.controller;

import com.dailyfit.exercise.Exercise;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface ExerciseController {
    ResponseEntity<List<Exercise>> getExercisesByQuery(String muscleGroup,
                                                       String type,
                                                       String name,
                                                       Integer difficulty,
                                                       Boolean material);
    void deleteExercise(String name) throws SQLException;
    void addExercisesByQuery(String muscleGroup,
                             String type,
                             String name,
                             Integer difficulty,
                             Boolean material) throws SQLException;
    void updateExercisesByQuery(String muscleGroup,
                                String type,
                                String name,
                                Integer difficulty,
                                Boolean material) throws SQLException;
}
