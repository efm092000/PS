package com.dailyfit.routine.controller;

import com.dailyfit.routine.Routine;
import com.dailyfit.routine.RoutineExerciseDTO;
import com.dailyfit.routine.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/routine")
public class RoutineControllerImpl implements RoutineController{
    private final RoutineService routineService;

    public RoutineControllerImpl(RoutineService routineService) {
        this.routineService = routineService;
    }


    @PostMapping(value = "/{name}")
    public ResponseEntity<Routine> createRoutine(@PathVariable String name,
                                                 @RequestParam String email) {
        try {
            Routine routine = routineService.createRoutine(name, email);
            return ResponseEntity.ok(routine);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/{rid}/exercise")
    public ResponseEntity<RoutineExerciseDTO> addExerciseToRoutine(@PathVariable int rid,
                                                   @RequestParam String name,
                                                   @RequestParam int sets,
                                                   @RequestParam int reps
    ) {
        try {
            return ResponseEntity.ok(routineService.addExerciseToRoutine(rid, name, sets, reps));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/{rid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Routine> getRoutineByRid(@PathVariable int rid) {
        try {
            Optional<Routine> routine = routineService.getRoutineByRid(rid);
            return routine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/{rid}/exercises")
    public ResponseEntity<List<RoutineExerciseDTO>> getExercisesByRid(@PathVariable int rid) {
        try {
            List<RoutineExerciseDTO> routineExercises = routineService.getRoutineExercises(rid);
            return ResponseEntity.ok(routineExercises);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = "/{rid}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable int rid,
                                                 @RequestParam String name,
                                                 @RequestParam String email) {
        Routine routine;

        try {
            routine = routineService.updateRoutine(rid, name, email);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(routine);
    }

    @DeleteMapping(value = "/{rid}")
    public ResponseEntity<String> deleteRoutine(@PathVariable int rid) {
        try {
            routineService.deleteRoutine(rid);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok("Routine was deleted successfully.");
    }
}