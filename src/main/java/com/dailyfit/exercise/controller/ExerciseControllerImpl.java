package com.dailyfit.exercise.controller;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.exercise.service.ExerciseService;
import com.dailyfit.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class ExerciseControllerImpl implements ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseControllerImpl(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping(value = "/api/exercise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Exercise> getExercisesByQuery(@RequestParam(defaultValue = "all") String muscleGroup,
                                                    @RequestParam(defaultValue = "all") String type,
                                                    @RequestParam(defaultValue = "all") String name,
                                                    @RequestParam(defaultValue = "all") String difficulty,
                                                    @RequestParam(defaultValue = "all") String material) {
        try {
            Optional<Exercise> optionalExercise = exerciseService.getExerciseByQuery(muscleGroup, type, name, difficulty, material);
            return optionalExercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
