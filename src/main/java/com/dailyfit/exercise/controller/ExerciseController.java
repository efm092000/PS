package com.dailyfit.exercise.controller;

import com.dailyfit.exercise.BooleanFilter;
import com.dailyfit.exercise.Exercise;
import com.dailyfit.exercise.IntegerFilter;
import org.springframework.core.io.Resource;
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
                             Boolean material,
                             String gif,
                             String description) throws SQLException;
    void updateExercisesByQuery(String muscleGroup,
                                String type,
                                String name,
                                Integer difficulty,
                                Boolean material,
                                String gif,
                                String description) throws SQLException;

    ResponseEntity<Resource> getImage(String imageName);

    ResponseEntity<BooleanFilter[]> getMaterial();
    ResponseEntity<String[]> getMuscleGroup();
    ResponseEntity<IntegerFilter[]> getDifficulty();
    ResponseEntity<String[]> getType();
}
