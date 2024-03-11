package com.dailyfit.exercise.controller;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.user.User;
import org.springframework.http.ResponseEntity;

public interface ExerciseController {
    ResponseEntity<Exercise> getExercisesByQuery(String muscleGroup,
                                                 String type,
                                                 String name,
                                                 String difficulty,
                                                 String material);
}
