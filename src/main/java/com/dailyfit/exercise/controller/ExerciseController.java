package com.dailyfit.exercise.controller;

import com.dailyfit.exercise.Exercise;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
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
}
