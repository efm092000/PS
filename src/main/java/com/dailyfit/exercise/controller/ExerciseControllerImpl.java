package com.dailyfit.exercise.controller;

import com.dailyfit.exercise.Exercise;
import com.dailyfit.exercise.service.ExerciseService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.sql.SQLException;
import java.util.List;

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
    public void deleteExercise(@RequestParam String name) throws SQLException {
        exerciseService.deleteExercise(name);
    }

    @PostMapping(value = "/api/exercise")
    public void addExercisesByQuery(@RequestParam String muscleGroup,
                                    @RequestParam String type,
                                    @RequestParam String name,
                                    @RequestParam Integer difficulty,
                                    @RequestParam Boolean material,
                                    @RequestParam(required = false) String gif,
                                    @RequestParam String description) throws SQLException {
        exerciseService.createExercise(new Exercise(name,
                material,
                muscleGroup,
                difficulty,
                type,
                (gif == null)? "sample.gif" : gif,
                description));
    }

    @PutMapping(value = "/api/exercise")
    public void updateExercisesByQuery(@RequestParam(required = false) String muscleGroup,
                                       @RequestParam(required = false) String type,
                                       @RequestParam String name,
                                       @RequestParam(required = false) Integer difficulty,
                                       @RequestParam(required = false) Boolean material,
                                       @RequestParam(required = false) String gif,
                                       @RequestParam(required = false) String description) throws SQLException {
        exerciseService.updateExercise(new Exercise(name,
                material,
                muscleGroup,
                difficulty,
                type,
                (gif == null)? "sample.gif" : gif,
                description));
    }

    @GetMapping("/api/exercise/image")
    public ResponseEntity<Resource> getImage(@RequestParam(required = false) String gif) {
        Resource imageResource = new ClassPathResource("images/" + gif);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_GIF)
                .body(imageResource);
    }
}
