package com.dailyfit.routine.controller;

import com.dailyfit.routine.Routine;
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
    public ResponseEntity<String> createRoutine(@PathVariable String name,
                                                 @RequestParam String email) {
        try {
            routineService.createRoutine(name, email);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok("Routine was created successfully");
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

    @GetMapping(value = "/{email}/routines")
    public ResponseEntity<List<Routine>> getUserRoutines(@PathVariable String email) {
        try {
            List<Routine> userRoutines = routineService.getUserRoutines(email);
            return ResponseEntity.ok(userRoutines);
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