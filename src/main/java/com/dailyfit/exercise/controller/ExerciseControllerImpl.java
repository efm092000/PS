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
    public ResponseEntity<List<Exercise>> getExercisesByQuery(
            @RequestParam(required = false) String muscleGroup,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) Boolean material) {
        try {
            if (name != null) {
                return ResponseEntity.ok(exerciseService.getExerciseByQuery(name));
            } else {
                return ResponseEntity.ok(exerciseService.getExercisesByQuery(muscleGroup, type, difficulty, material));
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(value = "/api/exercise")
    public void deleteExercise(@RequestParam(required = false) String name) throws SQLException {
        if (name == null) return;
        exerciseService.deleteExercise(name);
    }

    @PostMapping(value = "/api/exercise")
    public void addExercisesByQuery(@RequestParam(required = false) String muscleGroup,
                                    @RequestParam(required = false) String type,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer difficulty,
                                    @RequestParam(required = false) Boolean material) throws SQLException {
        if (name == null ||
                type == null ||
                muscleGroup == null ||
                difficulty == null ||
                material == null) return;
        exerciseService.createExercise(new Exercise(name,
                material,
                muscleGroup,
                difficulty,
                type));
    }

    @PutMapping(value = "/api/exercise")
    public void updateExercisesByQuery(@RequestParam(required = false) String muscleGroup,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer difficulty,
                                       @RequestParam(required = false) Boolean material) throws SQLException {
        if (name == null) return;
        exerciseService.updateExercise(new Exercise(name,
                material,
                muscleGroup,
                difficulty,
                type));
    }
}
