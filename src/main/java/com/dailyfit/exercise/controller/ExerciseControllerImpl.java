package com.dailyfit.exercise.controller;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.exercise.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
public class ExerciseControllerImpl implements ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseControllerImpl(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping(value = "/api/exercise")
    public ResponseEntity<String> getExercisesByQuery(
            @RequestParam(defaultValue = "all") String muscleGroup,
            @RequestParam(defaultValue = "all") String type,
            @RequestParam(defaultValue = "all") String name,
            @RequestParam(defaultValue = "all") String difficulty,
            @RequestParam(defaultValue = "all") String material) {
        try {
            if (!name.equals("all")) {
                Optional<String> optionalExercise = exerciseService.getExerciseByQuery(name);
                return optionalExercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            } else {
                Optional<String> exercises = exerciseService.getExercisesByQuery(muscleGroup, type, difficulty, material);
                return exercises.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(value = "/api/exercise")
    public void deleteExercise(@RequestParam(defaultValue = "all") String name) throws SQLException {
        exerciseService.deleteExercise(name);
    }

    @PostMapping(value = "/api/exercise")
    public void addExercisesByQuery(@RequestParam(defaultValue = "all") String muscleGroup,
                                    @RequestParam(defaultValue = "all") String type,
                                    @RequestParam(defaultValue = "all") String name,
                                    @RequestParam(defaultValue = "all") String difficulty,
                                    @RequestParam(defaultValue = "all") String material) throws SQLException {
        exerciseService.createExercise(new Exercise(name,
                material,
                muscleGroup,
                difficulty,
                type));
    }

    @PutMapping(value = "/api/exercise")
    public void updateExercisesByQuery(@RequestParam(defaultValue = "all") String muscleGroup,
                                       @RequestParam(defaultValue = "all") String type,
                                       @RequestParam(defaultValue = "all") String name,
                                       @RequestParam(defaultValue = "all") String difficulty,
                                       @RequestParam(defaultValue = "all") String material) throws SQLException {
        exerciseService.updateExercise(new Exercise(name,
                material,
                muscleGroup,
                difficulty,
                type));
    }
}
